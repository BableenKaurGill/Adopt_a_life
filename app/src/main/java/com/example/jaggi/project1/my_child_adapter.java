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

public class my_child_adapter extends RecyclerView.Adapter<my_child_viewholder> {

    JSONArray jsar;

    Activity b;

    public my_child_adapter(JSONArray jsonarr, Activity a)
    {

        jsar = jsonarr;
        b = a;
    }
    @Override
    public my_child_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        my_child_viewholder view = new my_child_viewholder(LayoutInflater.from(b).inflate(R.layout.my_child_cell,parent,false));
        return view;
    }

    @Override
    public void onBindViewHolder(my_child_viewholder holder, int position) {

        try {
            // iterating for each json object in json array
           final   JSONObject job = (JSONObject) jsar.get(position);

            // binding values from json object to cell layout via view holder
            holder.name_text.setText(job.getString("Child_name"));
            holder.gender_text.setText(job.getString("Gender"));
            holder.dob_text.setText(job.getString("Date_of_birth"));
            holder.living_text.setText(job.getString("Living_in"));
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        My_child_user.delete_child(job.getString("Child_id"), b);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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


}
