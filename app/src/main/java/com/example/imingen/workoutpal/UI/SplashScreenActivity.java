package com.example.imingen.workoutpal.UI;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.imingen.workoutpal.R;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * Heavliy inspired by
 * https://www.youtube.com/watch?v=gt1WYT0Qpfk
 * Activity does not do anything in background while splashscreen is up, it is just for fancy
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen splashScreen = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(1500)
                .withBackgroundColor(Color.parseColor("#13a48c"))
                .withLogo(R.drawable.logo)
                .withBeforeLogoText("WorkoutPal");

        splashScreen.getBeforeLogoTextView().setTextColor(Color.WHITE);
        splashScreen.getBeforeLogoTextView().setTextSize(50);

        View easySplashScreen = splashScreen.create();
        setContentView(easySplashScreen);
    }
}
