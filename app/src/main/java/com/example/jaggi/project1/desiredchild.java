package com.example.jaggi.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class desiredchild extends AppCompatActivity {

    private TextView username, useremail, usercontact , useraddress ;


    private TextView childname, childgender, childdob, childskin, disability , childliving , childheight , childbody , childeducation , profilefor;

    private TextView fathername , mothername  , guardian_name , occupation , community, religion , siblings  ;


    private ImageView profile_img ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desiredchild);


        profile_img = (ImageView) findViewById(R.id.profile_img);


        if(getIntent().getStringExtra("if_image").equals("yes")) {
            byte[] byteArray = getIntent().getByteArrayExtra("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            profile_img.setImageBitmap(bmp);
        }




        fathername  = (TextView) findViewById(R.id.fathername);

        mothername  = (TextView) findViewById(R.id.mothername);

        guardian_name = (TextView) findViewById(R.id.guardianname);

        occupation = (TextView) findViewById(R.id.occupation);

        community = (TextView) findViewById(R.id.community);

        religion = (TextView) findViewById(R.id.religion);

        siblings = (TextView) findViewById(R.id.siblings);

        profilefor = (TextView) findViewById(R.id.profilefor);




        username = (TextView) findViewById(R.id.username);

        useremail = (TextView) findViewById(R.id.useremail);

        usercontact = (TextView) findViewById(R.id.usercontact);

        useraddress = (TextView) findViewById(R.id.useraddress);

        username.setText(getIntent().getStringExtra("uname"));

        useremail.setText(getIntent().getStringExtra("uemail"));

        usercontact.setText(getIntent().getStringExtra("ucontact"));

        useraddress.setText(getIntent().getStringExtra("uaddress"));


        childname = (TextView) findViewById(R.id.childname);
        childgender = (TextView) findViewById(R.id.childgender);
      //  childaddress = (TextView) findViewById(R.id.address_et);
        childdob = (TextView) findViewById(R.id.childdob);

        childskin = (TextView) findViewById(R.id.childskin);
        childbody = (TextView) findViewById(R.id.childbody);
        childheight = (TextView) findViewById(R.id.childheight);
        childeducation = (TextView) findViewById(R.id.childeducation);
        childliving = (TextView) findViewById(R.id.childliving);
        disability = (TextView) findViewById(R.id.disability);

        childskin.setText(getIntent().getStringExtra("cskin"));
        childbody.setText(getIntent().getStringExtra("cbody_type"));
        childheight.setText(getIntent().getStringExtra("cheight"));
        childeducation.setText(getIntent().getStringExtra("cedu"));
        childliving.setText(getIntent().getStringExtra("cliving_in"));
        disability.setText(getIntent().getStringExtra("cdisablity"));

        childname.setText(getIntent().getStringExtra("cname"));
        childgender.setText(getIntent().getStringExtra("cgender"));
       // childaddress.setText(getIntent().getStringExtra(""));
        childdob.setText(getIntent().getStringExtra("cdob"));

        profilefor.setText(getIntent().getStringExtra("crelation"));

        fathername.setText(getIntent().getStringExtra("pFather_name"));
        mothername.setText(getIntent().getStringExtra("pMother_name"));
        guardian_name.setText(getIntent().getStringExtra("pGuardian_name"));
        occupation.setText(getIntent().getStringExtra("pOccupation"));
        community.setText(getIntent().getStringExtra("pCommunity"));
        religion.setText(getIntent().getStringExtra("pReligion"));
        siblings.setText(getIntent().getStringExtra("psibling"));




    }



    public void upload(View v) {

        JSONObject job = new JSONObject();

        try {
            job.put("child_id", getIntent().getStringExtra("child_id"));
            job.put("image", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Ipaddress.ip + "/upload_image.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getString("result").equals("done")) {
                        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);

                        if (sp.getString("user_type", "").equals("ngo")) {

                            Toast.makeText(desiredchild.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(desiredchild.this, Homepagengo.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(desiredchild.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(desiredchild.this, Homepage.class);
                            startActivity(i);
                            finish();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)

            {
                System.out.println(error);
            }
        });

        AppController app = new AppController(desiredchild.this);

        app.addToRequestQueue(jobreq);
    }
}




