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


public class Parentdetails extends AppCompatActivity {

    private EditText father_et , mother_et , guardian_et , occupation_et , community_et , religion_et , siblings_et ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parentdetails);

        father_et = (EditText) findViewById(R.id.father_et);
        mother_et = (EditText) findViewById(R.id.mother_et);
        guardian_et = (EditText) findViewById(R.id.guardian_et);
        occupation_et = (EditText) findViewById(R.id.occupation_et);
        community_et = (EditText) findViewById(R.id.community_et);
        religion_et = (EditText) findViewById(R.id.religion_et);
        siblings_et = (EditText) findViewById(R.id.siblings_et);
    }

    public void createprofile(View view) {

        String father =father_et.getText().toString();
        String mother =mother_et.getText().toString();
        String guardian =guardian_et.getText().toString();
        String occupation  =occupation_et.getText().toString();
        String community =community_et.getText().toString();
        String religion =religion_et.getText().toString();
        String siblings =siblings_et.getText().toString();



        if (guardian.length() < 4 || !guardian.matches("[a-zA-Z ]+")) {
            Toast.makeText(Parentdetails.this, " please enter guardian's name and it must be 4 character long and not contain any digits", Toast.LENGTH_SHORT).show();
            return;
        }


        JSONObject jobj = new JSONObject();

        try {
            jobj.put("f" ,father);
            jobj.put("m" ,mother);
            jobj.put("g" ,guardian);
            jobj.put("o" ,occupation);
            jobj.put("c" ,community);
            jobj.put("r" ,religion);
            jobj.put("s" ,siblings);
            jobj.put("child_id" , getIntent().getStringExtra("child_id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jobj);

        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+Ipaddress.ip+"/parent_details.php",
                jobj, new Response.Listener<JSONObject>()

        {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);

                try {
                    if(response.getString("key").equals("done"))
                    {
                        Toast.makeText(Parentdetails.this ,"done" , Toast.LENGTH_SHORT).show();


                            Intent  i = new Intent(Parentdetails.this, Profilephoto.class);
                            i.putExtra("child_id" , getIntent().getStringExtra("child_id") );
                            startActivity(i);
                            finish();


                    }

                    else {
                        Toast.makeText(Parentdetails.this ,"error" , Toast.LENGTH_SHORT).show();

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

        AppController app = new AppController(Parentdetails.this);

        app.addToRequestQueue(jobjreq);

    }


}
