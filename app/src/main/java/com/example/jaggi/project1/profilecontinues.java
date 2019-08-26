package com.example.jaggi.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DatePickerDialog;

import android.view.View;
import android.widget.RadioButton;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;



public class profilecontinues extends AppCompatActivity {
    int year;
    int month;
    int day;
    EditText dateedt;
    private EditText name_et , skin_et , living_et , height_et , body_et , education_et ;
    private RadioButton male_rb;
    private RadioButton female_rb;
    private RadioButton disability_rb;
    private RadioButton disabilityno_rb;
    public String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilecontinues);

        dateedt = (EditText) findViewById(R.id.datpckr);
        Calendar c = Calendar.getInstance();
        dateedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                year = mcurrentDate.get(Calendar.YEAR);
                month = mcurrentDate.get(Calendar.MONTH);
                day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog mDatePicker = new DatePickerDialog(profilecontinues.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        dateedt.setText(new StringBuilder().append(selectedday).append("/").append(selectedmonth).append("/").append(selectedyear));
                        int month_k = selectedmonth + 1;

                    }
                }, year, month, day);
                mDatePicker.setTitle("Please select date");
                // TODO Hide Future Date Here
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

                // TODO Hide Past Date Here
                //  mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePicker.show();

            }
        });


        name_et = (EditText) findViewById(R.id.name_et);
        skin_et = (EditText) findViewById(R.id.skin_et);
        living_et = (EditText) findViewById(R.id.living_et);
        height_et = (EditText) findViewById(R.id.height_et);
        body_et = (EditText) findViewById(R.id.body_et);
        education_et = (EditText) findViewById(R.id.education_et);
        male_rb = (RadioButton)findViewById(R.id.male_rb);
        female_rb = (RadioButton)findViewById(R.id.female_rb);
        disability_rb = (RadioButton)findViewById(R.id.disability_rb);
        disabilityno_rb = (RadioButton)findViewById(R.id.disabilityno_rb);
    }


    public void submit(View view){
        String Child_name = name_et.getText().toString();
        String skin = skin_et.getText().toString();
        String living =living_et.getText().toString();
        String height = height_et.getText().toString();
        String body = body_et.getText().toString();
        String date = dateedt.getText().toString();
        String education = education_et.getText().toString();
        Boolean male = male_rb.isChecked();
        Boolean female = female_rb.isChecked();
        Boolean disability = disability_rb.isChecked();
        Boolean disabilityno = disabilityno_rb.isChecked();

        if (Child_name.length() < 4 || !Child_name.matches("[a-zA-Z ]+")) {
            Toast.makeText(profilecontinues.this, "name must be 4 character long and not contain any digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if (living.equals("")) {
            Toast.makeText(profilecontinues.this, "living in is blank", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!male&&!female){
            Toast.makeText(profilecontinues.this, "select gender", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!disability&&!disabilityno){
            Toast.makeText(profilecontinues.this, "select disability", Toast.LENGTH_SHORT).show();
            return;
        }


        JSONObject job = new JSONObject();

        try {
            job.put("n", Child_name);
            job.put("s", skin);
            job.put("l", living);
            job.put("h", height);
            job.put("b", body);
            if(male){

                job.put("g","male");
            }

            if(female)
            {
                job.put("g","female");
            }

            if(disability)
            {
                job.put("d" , "disable");
            }

            if(disabilityno)
            {
                job.put("d" , "disableno");
            }
            job.put("e", education);
            job.put("da", date);
            job.put("relation" , getIntent().getStringExtra("profile_for"));
            job.put("added_by" , getIntent().getStringExtra("usertype"));
            SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);

            if(sp.getString("user_type","").equals("user"))
            {
                job.put("added_by_id" , sp.getString("user_id",""));
            }
            if(sp.getString("user_type","").equals("ngo"))
            {
                job.put("added_by_id" , sp.getString("ngo_id",""));
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(job);

        JsonObjectRequest json = new JsonObjectRequest("http://"+Ipaddress.ip+"/child_details.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("key").equals("done"))
                    {

                        Toast.makeText(profilecontinues.this , "done" , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent (profilecontinues.this ,Parentdetails.class);
                        i.putExtra("usertype",getIntent().getStringExtra("usertype"));
                        i.putExtra("child_id",response.getString("child_id"));
                        startActivity(i);

                        finish();
                    }

                    else
                    {
                        Toast.makeText(profilecontinues.this , "error" , Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        json.setRetryPolicy(new DefaultRetryPolicy(20000, 3, 2));
        AppController app = new AppController(profilecontinues.this);

        app.addToRequestQueue(json);


    }


}

