package com.example.jaggi.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Search_child extends AppCompatActivity {

    RecyclerView recycle ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_child);

        recycle = (RecyclerView) findViewById(R.id.recycler);
    }

    public void search(View view) {

        EditText search_et = (EditText) findViewById(R.id.search_et);
        String sear =  search_et.getText().toString();

        if(sear.equals(""))
        {
            Toast.makeText(Search_child.this , "please type something " ,Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject job = new JSONObject();

        try {
            job.put("name" , sear);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(job);
        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/search_child.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                JSONArray jsarr = null;
                try {
                    jsarr = response.getJSONArray("key");
                    profiles_adapter ad = new profiles_adapter(jsarr,Search_child.this);
                    recycle.setLayoutManager(new LinearLayoutManager(Search_child.this,LinearLayoutManager.VERTICAL,false));
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

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000,2 , 2));

        AppController app = new AppController(Search_child.this);

        app.addToRequestQueue(jobreq);
    }
}
