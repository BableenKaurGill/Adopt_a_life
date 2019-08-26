package com.example.jaggi.project1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class profiles_fragment extends Fragment {
    RecyclerView recycle ;
    LinearLayout search_layout ;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_profiles,container,false);

        recycle = (RecyclerView) v. findViewById(R.id.recycler);

        search_layout = (LinearLayout) v.findViewById(R.id.search_layout);
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity() , Search_child.class);
                startActivity(i);
            }
        });

        get_profiles();
        return v;
    }
    public void get_profiles(){
        JsonObjectRequest job = new JsonObjectRequest("http://"+Ipaddress.ip+"/child_profiles.php", new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    System.out.println(response);

                    JSONArray jsarr = response.getJSONArray("key");
                    profiles_adapter ad = new profiles_adapter(jsarr,getActivity());
                    recycle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                    recycle.setAdapter(ad);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);
            }
        });

        job.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 ,2));

        AppController app = new AppController(getActivity());

        app.addToRequestQueue(job);
    }
}