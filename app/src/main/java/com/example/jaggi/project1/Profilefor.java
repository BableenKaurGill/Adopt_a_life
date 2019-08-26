package com.example.jaggi.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profilefor extends AppCompatActivity {

    Button skip_btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilefor);

        skip_btn = (Button) findViewById(R.id.skip_btn);

        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
        if(sp.getString("user_type","").equals("user"))
        {
            skip_btn.setVisibility(View.VISIBLE);
        }
    }

    public void client_click(View view) {

        Intent i = new Intent(Profilefor.this, profilecontinues.class);
        i.putExtra("profile_for", "client");
        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
        i.putExtra("usertype",sp.getString("user_type",""));

        startActivity(i);
    }

    public void friend_click(View view) {
        Intent i = new Intent(Profilefor.this, profilecontinues.class);

        i.putExtra("profile_for", "friend");
        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
        i.putExtra("usertype",sp.getString("user_type",""));
        startActivity(i);
    }

    public void niece_click(View view) {
        Intent i = new Intent(Profilefor.this, profilecontinues.class);

        i.putExtra("profile_for", "niece");
        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
        i.putExtra("usertype",sp.getString("user_type",""));
        startActivity(i);
    }

    public void nephew_click(View view) {
        Intent i = new Intent(Profilefor.this, profilecontinues.class);
        i.putExtra("profile_for", "nephew");
        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
        i.putExtra("usertype",sp.getString("user_type",""));

        startActivity(i);
    }

    public void sister_click(View view) {
        Intent i = new Intent(Profilefor.this, profilecontinues.class);
        i.putExtra("profile_for", "sister");
        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
        i.putExtra("usertype",sp.getString("user_type",""));

        startActivity(i);
    }

    public void brother_click(View view) {
        Intent i = new Intent(Profilefor.this, profilecontinues.class);
        i.putExtra("profile_for", "brother");
        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
        i.putExtra("usertype",sp.getString("user_type",""));

        startActivity(i);
    }


    public void daughter_click(View view) {
        Intent i = new Intent(Profilefor.this, profilecontinues.class);
        i.putExtra("profile_for", "daughter");
        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
        i.putExtra("usertype",sp.getString("user_type",""));

        startActivity(i);
    }


    public void son_click(View view) {
        Intent i = new Intent(Profilefor.this, profilecontinues.class);
        i.putExtra("profile_for", "son");
        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
        i.putExtra("usertype",sp.getString("user_type",""));

        startActivity(i);
    }

    public void go_user_home(View view) {

        Intent i = new Intent(Profilefor.this, Homepage.class);

        startActivity(i);

        finish();
    }
}
