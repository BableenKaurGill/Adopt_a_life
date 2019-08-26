package com.example.jaggi.project1;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Signin extends AppCompatActivity {
    EditText Email_et , Password_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Email_et = (EditText) findViewById(R.id.Email_et);
        Password_et = (EditText) findViewById(R.id.Password_et);

    }


    public void signup(View view) {
        if(getIntent().getStringExtra("usertype").equals("user"))
        {
            Intent i= new Intent(Signin.this,Usersignup.class);
            startActivity(i);
        }
            else
        {
            Intent i= new Intent(Signin.this,Ngosignup.class);
            startActivity(i);
        }
    }

    public void signin(View view)
    {
        final String Email = Email_et.getText().toString();

        String Password = Password_et.getText().toString();

        if(Email.equals(""))
        {
            Toast.makeText(Signin.this , "please enter your email" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (Password.equals(""))
        {
            Toast.makeText(Signin.this , "please enter your password" , Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject job = new JSONObject();

        try {
            job.put("Email_key" , Email);
            job.put("Password" , Password);
            job.put("usertype" , getIntent().getStringExtra("usertype"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/login.php", job,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getString("key").equals("done")) {
                                Toast.makeText(Signin.this, "logged in", Toast.LENGTH_SHORT).show();

                                if (getIntent().getStringExtra("usertype").equals("user"))
                                {

                                    SharedPreferences.Editor sp = getSharedPreferences("user_info",MODE_PRIVATE).edit();
                                    sp.putString("user_email" , Email);
                                    sp.putString("user_id",response.getString("user_id"));
                                    sp.putString("user_type","user");
                                    sp.commit();

                                    Intent i = new Intent(Signin.this, Homepage.class);

                                    startActivity(i);
                                } else {
                                    SharedPreferences.Editor sp = getSharedPreferences("user_info",MODE_PRIVATE).edit();
                                    sp.putString("ngo_id",response.getString("ngo_id"));
                                    sp.putString("user_type","ngo");
                                    sp.commit();

                                    Intent i = new Intent(Signin.this, Homepagengo.class);
                                    startActivity(i);
                                }
                            }
                            else {
                                Toast.makeText(Signin.this , "please check your email and password again" , Toast.LENGTH_SHORT).show();
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

        jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000 , 2 ,2));

        AppController app = new AppController(Signin.this);
        app.addToRequestQueue(jobjreq);
    }

    public void reset_password(View view)
    {
        if(getIntent().getStringExtra("usertype").equals("user"))
        {
            Intent i= new Intent(Signin.this,Resetpassword.class);
            startActivity(i);
        }
        else
        {
            Intent i= new Intent(Signin.this,Resetngopassword.class);
            startActivity(i);
        }
    }
}



