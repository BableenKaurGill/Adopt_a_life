package com.example.jaggi.project1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Viewfeedback extends AppCompatActivity {
    RecyclerView recycle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfeedback);

        recycle = (RecyclerView) findViewById(R.id.recyclerview);
        get_feedback();
        return ;



    }

    public void get_feedback(){

        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);


        JSONObject jobj = new JSONObject();

        try {

            jobj.put("ngo_id" , sp.getString("ngo_id","") );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest job = new JsonObjectRequest("http://"+Ipaddress.ip+"/viewfeedback.php", jobj , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsarr =  response.getJSONArray("key");

                    viewfeed_adapter ad = new viewfeed_adapter(jsarr , Viewfeedback.this);

                    recycle.setLayoutManager(new LinearLayoutManager(Viewfeedback.this,LinearLayoutManager.VERTICAL,false));

                    recycle.setAdapter(ad);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        job.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 ,2));

        AppController app = new AppController(Viewfeedback.this);

        app.addToRequestQueue(job);
    }

}
