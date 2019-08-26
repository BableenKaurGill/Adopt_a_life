package com.example.jaggi.project1;

import android.app.DatePickerDialog;
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


import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;



public class Feedback extends AppCompatActivity {
    EditText feedback_et ,date_et ,  ngo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback_et = (EditText) findViewById(R.id.feedback_et);
        date_et = (EditText) findViewById(R.id.date_et);
        ngo = (EditText) findViewById(R.id.ngo_name);
    }

    public void send(View view) {
        String Feedback = feedback_et.getText().toString();
        String Date = date_et.getText().toString();
        String ngo_name = ngo.getText().toString();


        if (ngo_name.length() < 8|| !ngo_name.matches("[a-zA-Z ]+")) {

            Toast.makeText(Feedback.this, "name must be 8 character long and not contain any digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Date.equals("")) {
            Toast.makeText(Feedback.this, "enter the date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Feedback.equals("")){
            Toast.makeText(Feedback.this,"enter the feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject json = new JSONObject();

        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);

        try{
            json.put("n",ngo_name);
            json.put("d",Date);
            json.put("f",Feedback);
            json.put("saved_email",sp.getString("user_email",""));
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        System.out.println(json);
        JsonObjectRequest req = new JsonObjectRequest("http://" + Ipaddress.ip + "/feedback.php", json,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("key").equals("0")) {
                        Toast.makeText(Feedback.this, "error", Toast.LENGTH_SHORT).show();

                    } else if (response.getString("key").equals("1")) {
                        Toast.makeText(Feedback.this, "done", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(Feedback.this, "error", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                {
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);

                    }
                });
        req.setRetryPolicy(new DefaultRetryPolicy(2000, 2, 2));

        AppController app = new AppController(Feedback.this);

        app.addToRequestQueue(req);


    }

    public void cancel(View view) {
        Intent i = new Intent(Feedback.this,Homepage.class);
        startActivity(i);
    }

    public void select_date(View view) {

        Calendar mcurrentDate = Calendar.getInstance();
        int year=mcurrentDate.get(Calendar.YEAR);
        int month=mcurrentDate.get(Calendar.MONTH);
        int day=mcurrentDate.get(Calendar.DAY_OF_MONTH);



        final DatePickerDialog mDatePicker = new DatePickerDialog(Feedback.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datepicker, int year, int month, int day) {

                String date_s = String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year);

                date_et.setText(date_s);


            }
        }, year, month, day);
        mDatePicker.setTitle("Please select date");

        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());

        mDatePicker.show();

    }
}
