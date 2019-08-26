package com.example.jaggi.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Resetngousingemail extends AppCompatActivity {
    EditText email_et;
    Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetngousingemail);
        email_et = (EditText) findViewById(R.id.email_et);
        next_btn =(Button) findViewById(R.id.next_btn);

    }

    public void next_btn (View v){

        String email = email_et.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.contains("_")) {
            Toast.makeText(Resetngousingemail.this, "please enter valid email type", Toast.LENGTH_SHORT).show();
            return;
        }

        int randompin = (int) (Math.random()*9000);

        Intent i = new Intent(Resetngousingemail.this ,Otpngo.class);

        i.putExtra("email_key",email);
        i.putExtra("pin_key", String.valueOf(randompin));

        startActivity(i);
        finish();
    }
}
