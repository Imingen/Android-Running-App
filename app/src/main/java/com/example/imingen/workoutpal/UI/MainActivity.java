package com.example.imingen.workoutpal.UI;

import android.content.Intent;
import android.os.Bundle;
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
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.fragments.NavigationDrawerFragment;
import com.example.imingen.workoutpal.models.Run;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Marius on 13.10.2017.
 */

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationDrawerFragment navigationDrawerFragment;
    private String currentDate;
    private static final String ERROR_NO_NUMBER_OF_LAPS = "Please enter the desired number of laps";
    private static final String ERROR_NO_LENGTH_OF_LAPS = "Please enter the desired length of the laps";
    //private double lengthOfLaps;
   // private int numberOfLaps;
    private SimpleDateFormat d;
    private EditText nLaps;

    private NumberPicker minutePicker;
    private NumberPicker secondsPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpDrawer();

        currentDate = getCurrentDate();
        TextView dateTW = (TextView) findViewById(R.id.dateEditText);
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
    }


    public void startRun(View view){
        if(nLaps.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), ERROR_NO_NUMBER_OF_LAPS, Toast.LENGTH_LONG).show();
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
        d = new SimpleDateFormat("E MMM dd, yyyy");
        String dateString = d.format(date);
        return dateString;
    }

    @Override
    protected void onStart() {
        navigationDrawerFragment.updateCheckedItem(R.id.nav_main);
        super.onStart();
    }



}