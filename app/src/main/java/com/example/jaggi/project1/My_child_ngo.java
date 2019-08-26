package com.example.jaggi.project1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class My_child_ngo extends AppCompatActivity {

    private static RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_child_ngo);


        recycle = (RecyclerView) findViewById(R.id.recycler);

        search(My_child_ngo.this);
    }

    public static void search(final Activity a) {

        SharedPreferences sp = a.getSharedPreferences("user_info", MODE_PRIVATE);
        String ngo_id = sp.getString("ngo_id", "");

        JSONObject job = new JSONObject();

        try {
            job.put("id_key" , ngo_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(job);
        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/get_ngo_child.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                JSONArray jsarr = null;
                try {
                    jsarr = response.getJSONArray("key");
                    my_childngo_adapter ad = new my_childngo_adapter(jsarr,a);
                    recycle.setLayoutManager(new LinearLayoutManager(a,LinearLayoutManager.VERTICAL,false));
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

        AppController app = new AppController(a);

        app.addToRequestQueue(jobreq);
    }

    public static void delete_child(String child_id , final Activity a)
    {
        JSONObject job = new JSONObject();

        try {
            job.put("child_id" , child_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/delete_childngo.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);


                try {
                    if(response.getString("key").equals("done"))
                    {
                        Toast.makeText(a , "child  deleted", Toast.LENGTH_SHORT).show();
                        search(a);
                    }
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

        AppController app = new AppController(a);

        app.addToRequestQueue(jobreq);
    }
    }
