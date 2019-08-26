package com.example.jaggi.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Newpassword extends AppCompatActivity {
    EditText newpassword_et;
    String email;
    String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);


        contact = getIntent().getStringExtra("contact_key");
        newpassword_et =(EditText)findViewById(R.id.newpassword_et);

    }
    public void setpass (View v){

        String newpassword =  newpassword_et.getText().toString();
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";

        if (!newpassword.matches(pattern) || newpassword.length() < 8) {
            Toast.makeText(Newpassword.this, "password must contain atleast one alphabet , digit , special character and length must be 8 character", Toast.LENGTH_SHORT).show();
            return;
        }



        JSONObject json =new JSONObject();


        try {
            json.put("n",newpassword);

            json.put("c",contact);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/updatepassuser.php", json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("key").equals("done"))
                    {
                        Toast.makeText( Newpassword.this ,"password updated successfully" , Toast.LENGTH_SHORT).show();


                            Intent i = new Intent(Newpassword.this, Signin.class);
                            startActivity(i);

                        finish();

                    }
                    else {
                        Toast.makeText( Newpassword.this ,"error try again" , Toast.LENGTH_SHORT).show();

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

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2 , 2));

        AppController app = new AppController(Newpassword.this);

        app.addToRequestQueue(jobreq);

    }
}

