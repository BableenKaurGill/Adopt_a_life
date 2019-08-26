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

import org.json.JSONException;
import org.json.JSONObject;

public class Ngosignup extends AppCompatActivity {

    String loc = "";
    private EditText Name_et , Email_et , Password_et , Contact_et ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngosignup);

        Name_et = (EditText) findViewById(R.id.Name_et);
        Email_et = (EditText) findViewById(R.id.Email_et);
        Password_et = (EditText) findViewById(R.id.Password_et);
        Contact_et = (EditText) findViewById(R.id.Contact_et);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("", "Place: " + place.getName());

                loc = place.getAddress().toString();


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("", "An error occurred: " + status);
            }
        });
    }

    public void submitt(View v)
    {
        String ngo_name =Name_et.getText().toString();
        String Email = Email_et.getText().toString();
        String Password =Password_et.getText().toString();
        String Contact  = Contact_et.getText().toString();
        String Address = loc;

        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";

        if (ngo_name.length() < 8|| !ngo_name.matches("[a-zA-Z ]+")) {

            Toast.makeText(Ngosignup.this, "name must be 8 character long and not contain any digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches() || Email.contains("_")) {
            Toast.makeText(Ngosignup.this, "please enter valid email type", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Contact.length() < 10) {
            Toast.makeText(Ngosignup.this, "please enter valid contact details", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Password.matches(pattern) || Password.length() < 8) {
            Toast.makeText(Ngosignup.this, "password must contain atleast one alphabet , digit , special character and length must be 8 character", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Address.equals("")) {
            Toast.makeText(Ngosignup.this, "enter your address", Toast.LENGTH_SHORT).show();
            return;
        }



        JSONObject jobj = new JSONObject();

        try {
            jobj.put("Name_key" ,ngo_name);
            jobj.put("Email_key" ,Email);
            jobj.put("Password_key" ,Password);
            jobj.put("Contact_key" ,Contact);
            jobj.put("Address_key" ,Address);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/submittngo.php",
                jobj,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(Ngosignup.this ,"email already exist" , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("1")) {
                        Toast.makeText(Ngosignup.this ,"done" , Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor sp = getSharedPreferences("user_info",MODE_PRIVATE).edit();
                        sp.putString("ngo_id",response.getString("ngo_id"));
                        sp.putString("user_type","ngo");
                        sp.commit();
                        Intent i = new Intent (Ngosignup.this ,Profilefor.class);

                        startActivity(i);

                        finish();
                    }

                    else {
                        Toast.makeText(Ngosignup.this ,"error" , Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);
            }
        });


        jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000, 3 , 2));

        AppController app = new AppController(Ngosignup.this);

        app.addToRequestQueue(jobjreq);

    }


}
