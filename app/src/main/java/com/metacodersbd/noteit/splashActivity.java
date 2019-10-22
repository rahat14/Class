package com.metacodersbd.noteit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            getSupportActionBar().hide();
        }
        catch (Exception r )
        {

        }




        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(getApplicationContext()  , testActivity.class);
                startActivity(i);
                finish();


            }
        },3000) ;


    }
}
