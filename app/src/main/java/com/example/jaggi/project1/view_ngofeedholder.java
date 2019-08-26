package com.example.jaggi.project1;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;


/**
 * Created by jaggi on 5/5/2017.
 */

public class view_ngofeedholder extends RecyclerView.ViewHolder {

    TextView name_tv, date_tv, feed_tv;

    public view_ngofeedholder(View itemView) {
        super(itemView);

        name_tv = (TextView) itemView.findViewById(R.id.name_tv);

        date_tv= (TextView) itemView.findViewById(R.id.date_tv);


        feed_tv= (TextView) itemView.findViewById(R.id.feed_tv);

    }
}
