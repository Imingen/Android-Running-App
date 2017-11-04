package com.example.imingen.workoutpal.UI;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.fragments.FinishedRunFragment;
import com.example.imingen.workoutpal.fragments.NavigationDrawerFragment;

import java.util.HashMap;
import java.util.Locale;

public class FinnishedRunActivity extends AppCompatActivity {

    TextToSpeech textToSpeech;
    private Toolbar toolbar;
    private NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finnished_run);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpDrawer();


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.ERROR){
                    Toast.makeText(getApplicationContext(), "Text to speech not supported on your device",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    textToSpeech.setLanguage(Locale.ENGLISH);
                    textToSpeech.setSpeechRate(0.9f);
                    textToSpeech("Congratulations, you finnished todays workout!");
                }
            }
        });
    }

    //TODO this method is kinda meh right?
    @Override
    public void onBackPressed() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
    }

    public void returnHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_main);
    }

    public void textToSpeech(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
        }
    }

    //Tatt fra https://stackoverflow.com/questions/27968146/texttospeech-with-api-21
    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId=this.hashCode() + "";
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }
}
