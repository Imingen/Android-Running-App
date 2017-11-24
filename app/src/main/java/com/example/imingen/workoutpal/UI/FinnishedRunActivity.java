package com.example.imingen.workoutpal.UI;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.fragments.FinishedRunFragment;
import com.example.imingen.workoutpal.fragments.NavigationDrawerFragment;
import com.example.imingen.workoutpal.helpers.NotificationHelper;
import com.example.imingen.workoutpal.models.Achievement;
import com.example.imingen.workoutpal.models.Run;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class FinnishedRunActivity extends AppCompatActivity {

    TextToSpeech textToSpeech;
    private Toolbar toolbar;
    private NavigationDrawerFragment navigationDrawerFragment;
    private ArrayList<Achievement> databaseAchievements;
    private ArrayList<Run> databaseRuns;
    private ArrayList<Achievement> achievements;

    private DatabaseReference userRef;
    private DatabaseReference userAchRef;

    private NotificationHelper notificationHelper;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;

    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finnished_run);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        setUpDrawer();
        notificationHelper = new NotificationHelper(getApplicationContext());
        startNotification("Workout complete!", "Good job m9-1");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String [] voice = prefs.getString("languages", "").split("_");

        String language = voice[0];
        String region = voice[1];
        locale = new Locale(language, region);
        locale.setDefault(locale);


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.ERROR){
                    Toast.makeText(getApplicationContext(), "Text to speech not supported on your device",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    textToSpeech.setLanguage(locale);
                    textToSpeech.setSpeechRate(0.9f);
                    Log.e("JAU2", locale.getLanguage());

                    if(locale.getLanguage().equals("fr")){
                        textToSpeech("Félicitations, vous avez terminé l'entraînement d'aujourd'hui!");
                    }
                    if(locale.getLanguage().equals("de")){
                        textToSpeech("Herzlichen Glückwunsch, Sie haben das Training heute beendet!!");
                    }
                    if(locale.getLanguage().equals("zh")){
                        textToSpeech("Gōngxǐ, nǐ jīntiān wánchéngle duànliàn");
                    }
                    if(locale.getLanguage().equals("no")){
                        textToSpeech("Gratulerer, du gjennomførte dagens økt");
                    }
                   if(locale.getLanguage().equals("en")){
                     textToSpeech("Congratulations, you finished today's workout!");
                    }
                }
            }
        });


        databaseAchievements = new ArrayList<>();
        databaseRuns = new ArrayList<>();
        achievements = new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //Create two databae references
        DatabaseReference achievementRef = FirebaseDatabase.getInstance().getReference().child("Achievements");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid());
        userAchRef = FirebaseDatabase.getInstance().getReference().child(auth.getUid()).child("Achievements");
        //Get all achievements from the database
        //Goes into all the runs for current user
        userRef.child("Runs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Loops trough all the runs a user have
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    databaseRuns.add(snapshot.getValue(Run.class));
                }
                Log.i("XD2", Integer.toString(databaseRuns.size()));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        achievementRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Achievement achievement = snapshot.getValue(Achievement.class);
                    databaseAchievements.add(achievement);
//                    Log.i("XD2", achievement.getAchievementDescription());
                }
                if(databaseAchievements != null){
                    if(databaseAchievements.size() > databaseRuns.size()){
                        userRef.child("Achievements").push().setValue(databaseAchievements.get(databaseRuns.size() - 1));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Loop trough both lists that have reference to the achievements and runs from database
        //and add a achievement for each run the user have completed


    }


    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationHelper.getNotificationManager().cancel(2);
    }

    //TODO this method is kinda meh right?
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void returnHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
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

    public void startNotification(String title, String message){
        builder = notificationHelper.getChannelNotification(title, message, FinnishedRunActivity.class);
        notificationManager = notificationHelper.getNotificationManager();
        notificationManager.notify(2, builder.build());
    }

}
