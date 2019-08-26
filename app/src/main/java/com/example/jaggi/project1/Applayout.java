package com.example.jaggi.project1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import pl.droidsonroids.gif.GifImageView;


public class Applayout extends AppCompatActivity {

    GifImageView crawling ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applayout);

        crawling = (GifImageView) findViewById(R.id.crawl_anim);
        Animation anim = AnimationUtils.loadAnimation(Applayout.this , R.anim.left_to_right);
        crawling.setAnimation(anim);
        anim.start();

        Handler handle = new Handler();

        handle.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Intent i=new Intent(Applayout.this,Identity.class);
                startActivity(i);
                finish();


            }
        },4000);
    }

}
