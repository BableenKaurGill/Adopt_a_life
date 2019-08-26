package com.example.jaggi.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Identity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);
    }

     public void continueasauser (View v)
    {

      Intent i = new Intent(Identity.this,Signin.class);
        i.putExtra("usertype","user");
      startActivity(i);

    }


    public void continueasango(View view) {
        Intent i = new Intent(Identity.this,Signin.class);
        i.putExtra("usertype","NGO");
        startActivity(i);
    }
}
