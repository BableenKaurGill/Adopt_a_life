package com.example.jaggi.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Onetimepassword extends AppCompatActivity {
    EditText otp_et;
    String pin;
    String email;
    String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onetimepassword);

        otp_et = (EditText) findViewById(R.id.otp_et);

        email = getIntent().getStringExtra("email_key");
        contact = getIntent().getStringExtra("contact_key");
        pin = getIntent().getStringExtra("pin_key");

        Toast.makeText(Onetimepassword.this, pin, Toast.LENGTH_SHORT).show();

    }

    public void verify(View v) {
        String otp = otp_et.getText().toString();


            if (otp.equals(pin)) {
                Toast.makeText(Onetimepassword.this, "code matched", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Onetimepassword.this, Newpassword.class);
                startActivity(i);



                i.putExtra("contact_key", contact);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(Onetimepassword.this, "code do not match", Toast.LENGTH_SHORT).show();
            }

    }
}


