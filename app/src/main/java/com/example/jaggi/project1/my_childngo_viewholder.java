package com.example.jaggi.project1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jaggi on 5/20/2017.
 */

public class my_childngo_viewholder extends RecyclerView.ViewHolder {

    TextView name_text , gender_text , dob_text , living_text;
    Button delete;
    public my_childngo_viewholder(View itemView) {
        super(itemView);
        name_text = (TextView) itemView.findViewById(R.id.name_text);
        gender_text = (TextView) itemView.findViewById(R.id.gender_text);
        dob_text = (TextView) itemView.findViewById(R.id.dob_text);
        living_text = (TextView) itemView.findViewById(R.id.living_text);

        delete = (Button) itemView.findViewById(R.id.delete_child);

    }
}
