package com.kaligotla.oms;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        splash = findViewById( R.id.splash );
    }

    @Override
    protected void onResume() {
        splash = findViewById( R.id.splash );
        splash.startAnimation( AnimationUtils.loadAnimation( this, R.anim.splash ) );
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep( 3200 );
                    startActivity( new Intent( Splash.this, MainActivity.class ) );
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } ).start();
        super.onResume();
    }
}