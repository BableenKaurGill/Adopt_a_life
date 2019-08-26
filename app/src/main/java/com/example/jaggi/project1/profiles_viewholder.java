package com.example.jaggi.project1;

import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by jaggi on 4/18/2017.
 */

public class profiles_viewholder extends RecyclerView.ViewHolder {

    TextView name_text , gender_text , dob_text , living_text;
    LinearLayout cell_id ;
    public profiles_viewholder(View itemView) {
        super(itemView);
       name_text = (TextView) itemView.findViewById(R.id.name_text);
        gender_text = (TextView) itemView.findViewById(R.id.gender_text);
       dob_text = (TextView) itemView.findViewById(R.id.dob_text);
        living_text = (TextView) itemView.findViewById(R.id.living_text);

        cell_id = (LinearLayout) itemView.findViewById(R.id.cell_id);

    }
}
