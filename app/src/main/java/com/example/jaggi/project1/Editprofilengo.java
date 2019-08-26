package com.example.jaggi.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Editprofilengo extends AppCompatActivity {
    EditText name_et, email_et, password_et, contact_et , address_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofilengo);

        name_et = (EditText) findViewById(R.id.name_et);
        email_et = (EditText) findViewById(R.id.email_et);
        password_et = (EditText) findViewById(R.id.password_et);
        contact_et = (EditText) findViewById(R.id.contact_et);
        address_et = (EditText) findViewById(R.id.address_et);








        get_values();

    }

    public void update_btn(View v) {
        JSONObject job = new JSONObject();
        String Email = email_et.getText().toString();

        String ngo_name = name_et.getText().toString();
        String Password = password_et.getText().toString();
        String Contact = contact_et.getText().toString();
        String Address = address_et.getText().toString();;


        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";

        if (ngo_name.length() < 4|| !ngo_name.matches("[a-zA-Z ]+")) {

            Toast.makeText(Editprofilengo.this, "name must be 8 character long and not contain any digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches() || Email.contains("_")) {
            Toast.makeText(Editprofilengo.this, "please enter valid email type", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Contact.length() < 10) {
            Toast.makeText(Editprofilengo.this, "please enter valid contact details", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Password.matches(pattern) || Password.length() < 8) {
            Toast.makeText(Editprofilengo.this, "password must contain atleast one alphabet , digit , special character and length must be 8 character", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Address.equals("")) {
            Toast.makeText(Editprofilengo.this, "enter your address", Toast.LENGTH_SHORT).show();
            return;
        }






        try {
            job.put("email_key", Email);
            job.put("name_key", ngo_name);
            job.put("password_key", Password);
            job.put("contact_key", Contact);
            job.put("address_key", Address);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(job);

        JsonObjectRequest obreq = new JsonObjectRequest("http://" + Ipaddress.ip + "/updatengo.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                System.out.println(response);

                try {
                    if(response.getString("key").equals("1")){
                        Toast.makeText(Editprofilengo.this , "updated successfully" , Toast.LENGTH_SHORT).show();

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

        obreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

        AppController app = new AppController(Editprofilengo.this);

        app.addToRequestQueue(obreq);
    }


    public void get_values() {
        JSONObject job = new JSONObject();

        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String ngo_id = sp.getString("ngo_id", "");

        try {
            job.put("ngo_id", ngo_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Ipaddress.ip + "/editngo.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    JSONArray arr = response.getJSONArray("key");

                    JSONObject job_response = (JSONObject) arr.get(0);

                    name_et.setText(job_response.getString("ngo_name"));
                    email_et.setText(job_response.getString("Email"));
                    password_et.setText(job_response.getString("Password"));
                    contact_et.setText(job_response.getString("Contact"));
                    address_et.setText(job_response.getString("Address"));

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

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

        AppController app = new AppController(Editprofilengo.this);

        app.addToRequestQueue(jobreq);
    }

}