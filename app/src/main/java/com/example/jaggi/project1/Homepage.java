package com.example.jaggi.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Homepage extends AppCompatActivity {

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        Fragment profiles_frag = new profiles_fragment();

        ft.replace(R.id.frame_id ,profiles_frag);

        ft.commit();

    }

    public void open_all_child(View v)
    {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment profiles_frag = new profiles_fragment();

        ft.replace(R.id.frame_id ,profiles_frag);

        ft.commit();

    }

    public void add_child(View v)
    {


        Intent i = new Intent(Homepage.this , Profilefor.class);
        startActivity(i);
    }

    public void open_account(View v)
    {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment acc_frag = new account_fragment();

        ft.replace(R.id.frame_id ,acc_frag);


        ft.commit();
    }

    public void opn_profile(View view) {
        Intent i = new Intent(Homepage.this,Editprofileuser.class);
        startActivity(i);
    }


    public void snd_feedback(View view) {
        Intent i = new Intent(Homepage.this,Feedback.class);
        startActivity(i);
    }

    public void logout(View view) {
        Intent i = new Intent(Homepage.this,Identity.class);
        startActivity(i);
    }

    public void share_app(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Download the app 'Adopt a Life' via play store now...";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    public void rate_app(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + this.getPackageName())));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }



    public void open_my_child(View view)

    {
        Intent i =new Intent(Homepage.this , My_child_user.class);

        startActivity(i);


    }

    public void delete_account(View view) {

        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String user_id = sp.getString("user_id", "");

        JSONObject job = new JSONObject();

        try {
            job.put("id_key" , user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(job);
        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/delete_my_account.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);


                try {

                    if(response.getString("key").equals("done"))

                    {
                        Toast.makeText(Homepage.this , "account deleted", Toast.LENGTH_SHORT).show();
                        finish();
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

        AppController app = new AppController(Homepage.this);

        app.addToRequestQueue(jobreq);
    }
}
      