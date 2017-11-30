package com.example.imingen.workoutpal.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 1;

    private Toolbar toolbar;
    private NavigationDrawerFragment navigationDrawerFragment;
    private String currentDate;
    private static final String ERROR_NO_NUMBER_OF_LAPS = "Please enter the desired number of laps";
    private static final String ERROR_NO_LENGTH_OF_LAPS = "Please enter the desired length of the laps";

    private SimpleDateFormat d;
    private EditText nLaps;
    TextView dateTW;

    private NumberPicker minutePicker;
    private NumberPicker secondsPicker;
    private Button startRunButton;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpDrawer();

        firebaseAuth = FirebaseAuth.getInstance();

        currentDate = getCurrentDate();
        dateTW = findViewById(R.id.dateEditText);
        dateTW.setText(currentDate);

        nLaps = findViewById(R.id.numberOfLapsEditText);

        minutePicker = findViewById(R.id.lapLengthMinutes);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(10);
        minutePicker.setWrapSelectorWheel(true);

        secondsPicker = findViewById(R.id.lapLengthSeconds);
        secondsPicker.setMinValue(0);
        secondsPicker.setMaxValue(59);
        secondsPicker.setWrapSelectorWheel(true);
        updateTimeUI();

        //I have used onclick from XML file trough out the app but running it on my testing device
        // (a samsung note 2) crashed the app, unless I added an setOnclicklistener like under
        startRunButton = findViewById(R.id.startRunButton);
        startRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nLaps.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), ERROR_NO_NUMBER_OF_LAPS, Toast.LENGTH_LONG).show();
                }
               else if(secondsPicker.getValue() == 0 && minutePicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(), ERROR_NO_LENGTH_OF_LAPS, Toast.LENGTH_LONG).show();
                }
                else{
                    Run run = createNewRun();
                    Intent intent = new Intent(MainActivity.this, RunActivity.class);
                    intent.putExtra("run", run);
                    startActivity(intent);
                }
            }
        });

        view = findViewById(R.id.main_activity);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
//        loadAchievements();
    }


    /**
     * Sets the time on the UI
     */
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

    /**
     * Creates the new run object with data that user has inputed and todays date
     * @return A run object
     */
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
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_main);
    }

    /**
     * Gets the current date
     * @return A string representation of the current date
     */
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

    /**
     * Used to load a set of achievements to the database. Only run when needed new or updated
     * achievements in the database
     */
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_signout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.signoutButton:
                FirebaseAuth.getInstance().signOut();
                sendToLoginPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}