package com.example.jaggi.project1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;


/**
 * Created by jaggi on 4/18/2017.
 */

public class profiles_adapter extends RecyclerView.Adapter<profiles_viewholder> {

    JSONArray jsar;

    Activity b;

    public profiles_adapter(JSONArray jsonarr, Activity a)
    {

        jsar = jsonarr;
        b = a;
    }
    @Override
    public profiles_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        profiles_viewholder view = new profiles_viewholder(LayoutInflater.from(b).inflate(R.layout.recyclerview,parent,false));
        return view;
    }

    @Override
    public void onBindViewHolder(profiles_viewholder holder, int position) {

        try {
            // iterating for each json object in json array
           final   JSONObject job = (JSONObject) jsar.get(position);

            // binding values from json object to cell layout via view holder
            holder.name_text.setText(job.getString("Child_name"));
            holder.gender_text.setText(job.getString("Gender"));
            holder.dob_text.setText(job.getString("Date_of_birth"));
            holder.living_text.setText(job.getString("Living_in"));
            holder.cell_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(b , desiredchild.class);

                    try {
                        i.putExtra("uname" ,job.getString("User_name") );
                        i.putExtra("uemail" , job.getString("Email"));
                        i.putExtra("ucontact" , job.getString("Contact"));
                        i.putExtra("uaddress" , job.getString("Address"));

                        if(job.getString("image").length() > 100) {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            try {
                                StringToBitMap(job.getString("image")).compress(Bitmap.CompressFormat.PNG, 100, stream);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            byte[] byteArray = stream.toByteArray();

                            i.putExtra("if_image", "yes");
                            i.putExtra("image", byteArray);
                        }
                        else {
                            i.putExtra("if_image", "no");
                        }

                        i.putExtra("child_id" , job.getString("Child_id") );
                        i.putExtra("cname" ,job.getString("Child_name") );
                        i.putExtra("cgender" ,job.getString("Gender") );
                        i.putExtra("cdob" ,job.getString("Date_of_birth") );
                        i.putExtra("cskin", job.getString("Skin_tone"));
                        i.putExtra("cdisablity" , job.getString("Any_disability"));
                        i.putExtra("cliving_in" ,job.getString("Living_in") );
                        i.putExtra("cheight" ,job.getString("Height") );
                        i.putExtra("cbody_type" ,job.getString("Body_type") );
                        i.putExtra("cedu" ,job.getString("Education_level") );
                        i.putExtra("crelation" ,job.getString("relation") );
                       // i.putExtra("image", job.getString("image"));

                        i.putExtra("pFather_name" , job.getString("Father_name"));
                        i.putExtra("pMother_name" , job.getString("Mother_name"));
                        i.putExtra("pGuardian_name" , job.getString("Guardian_name"));
                        i.putExtra("pOccupation" , job.getString("Occupation"));
                        i.putExtra("pCommunity" , job.getString("Community"));
                        i.putExtra("pReligion" , job.getString("Religion"));
                        i.putExtra("psibling" , job.getString("Siblings"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    b.startActivity(i);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsar.length();
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
