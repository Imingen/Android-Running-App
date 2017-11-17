package com.example.imingen.workoutpal.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.fragments.NavigationDrawerFragment;
import com.example.imingen.workoutpal.models.Achievement;
import com.example.imingen.workoutpal.models.Run;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by Marius on 13.10.2017.
 */

public class MainActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 1;

    private Toolbar toolbar;
    private NavigationDrawerFragment navigationDrawerFragment;
    private String currentDate;
    private static final String ERROR_NO_NUMBER_OF_LAPS = "Please enter the desired number of laps";
    private static final String ERROR_NO_LENGTH_OF_LAPS = "Please enter the desired length of the laps";
    //private double lengthOfLaps;
   // private int numberOfLaps;
    private SimpleDateFormat d;
    private EditText nLaps;
    TextView dateTW;

    private NumberPicker minutePicker;
    private NumberPicker secondsPicker;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpDrawer();

        firebaseAuth = FirebaseAuth.getInstance();

        currentDate = getCurrentDate();
        dateTW = (TextView) findViewById(R.id.dateEditText);
        dateTW.setText(currentDate);

        nLaps = (EditText) findViewById(R.id.numberOfLapsEditText);

        minutePicker = (NumberPicker) findViewById(R.id.lapLengthMinutes);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(10);
        minutePicker.setWrapSelectorWheel(true);

        secondsPicker = (NumberPicker) findViewById(R.id.lapLengthSeconds);
        secondsPicker.setMinValue(0);
        secondsPicker.setMaxValue(59);
        secondsPicker.setWrapSelectorWheel(true);
        updateTimeUI();


        loadAchievements();

    }


    public void updateTimeUI(){
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                dateTW.setText(getCurrentDate());
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(run);
    }

    public void startRun(View view){
        if(nLaps.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), ERROR_NO_NUMBER_OF_LAPS, Toast.LENGTH_LONG).show();
        }
        if(secondsPicker.getValue() == 0){
            Toast.makeText(getApplicationContext(), ERROR_NO_LENGTH_OF_LAPS, Toast.LENGTH_LONG).show();
        }
        else{
            Run run = createNewRun();
            Intent intent = new Intent(this, RunActivity.class);
            intent.putExtra("run", run);
            startActivity(intent);
        }
    }

    private Run createNewRun() {
        Date date = null;
        int minutes = minutePicker.getValue();
        int seconds = secondsPicker.getValue();
        int numberOfLaps = Integer.parseInt(String.valueOf(nLaps.getText()));
        try {
            date = d.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            
        }
        Run run = new Run(date, minutes, seconds, numberOfLaps);
        return run;
    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_main);
    }

    private String getCurrentDate() {
        long date = System.currentTimeMillis();
        d = new SimpleDateFormat("E MMM dd, yyyy \n HH:mm");
        String dateString = d.format(date);
        return dateString;
    }

    private void sendToLoginPage(){
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public void loadAchievements(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Achievements");
        List<Achievement> ach = Achievement.achievementExampleData();

        for(Achievement a : ach){
            Log.i("XD", a.getAchievementName());
            databaseReference.child(a.getAchievementName()).setValue(a);
        }
    }

    @Override
    protected void onStart() {
        navigationDrawerFragment.updateCheckedItem(R.id.nav_main);
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null){
            sendToLoginPage();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_signout, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.signoutButton:
                firebaseAuth.getInstance().signOut();
                sendToLoginPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}