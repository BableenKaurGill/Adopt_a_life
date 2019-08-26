package com.example.jaggi.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Resetngopassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetngopassword);
    }

    public void send_contact(View view) {

        Intent i= new Intent(Resetngopassword.this,Resetngousingcontact.class);
        i.putExtra("usertype","contact");
        startActivity(i);
    }

    public void send_email(View view) {

        Intent i= new Intent(Resetngopassword.this,Resetngousingemail.class);
        i.putExtra("usertype","email");
        startActivity(i);
    }
}
