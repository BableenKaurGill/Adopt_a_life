package com.example.jaggi.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Resetusingcontact extends AppCompatActivity {
    EditText contact_et;
    Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetusingcontact);
        contact_et = (EditText) findViewById(R.id.contact_et);
        next_btn =(Button) findViewById(R.id.next_btn);

    }

    public void next_btn (View v){

        String contact = contact_et.getText().toString();

        if(contact.length() < 10)
        {
            Toast.makeText(Resetusingcontact.this , "please enter valid contact details", Toast.LENGTH_SHORT).show();
            return;
        }

        int randompin = (int) (Math.random()*9000);

        Intent i = new Intent(Resetusingcontact.this ,Onetimepassword.class);


        i.putExtra("contact_key",contact);
        i.putExtra("pin_key", String.valueOf(randompin));

        startActivity(i);
        finish();
    }
}

