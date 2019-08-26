package com.example.jaggi.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Otpngo extends AppCompatActivity {
    EditText otp_et;
    String pin;
    String email;
    String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpngo);
        otp_et = (EditText) findViewById(R.id.otp_et);


        contact = getIntent().getStringExtra("contact_key");
        pin = getIntent().getStringExtra("pin_key");

        Toast.makeText(Otpngo.this, pin, Toast.LENGTH_SHORT).show();

    }

    public void verify(View v) {
        String otp = otp_et.getText().toString();



            if (otp.equals(pin)) {
                Toast.makeText(Otpngo.this, "code matched", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Otpngo.this, Newpswdngo.class);
                startActivity(i);


                i.putExtra("contact_key", contact);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(Otpngo.this, "code do not match", Toast.LENGTH_SHORT).show();
            }

    }
}
