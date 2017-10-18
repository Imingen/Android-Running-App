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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    //private double lengthOfLaps;
   // private int numberOfLaps;
    private SimpleDateFormat d;
    private EditText nLaps;
    private EditText lLaps;

    //ToDo: Add more info to RUN.class. Se detaljert prosjektbeskrivelse. Skal egentlig være en abstrakt klasse. Må hvertfall legge til intervall info
    //ToDo: I den nye aktiviteten; Implementer logikk for å starte, stoppe og pause et run. Kunne ta tid som minimum
    // TODO: Implementer riktig backstack
    // TODO: se over om dato er riktig i objektet som blir satt opp i main og sendt til run aktiviteten
    //TODO: Mulig endre på timer siden Handler er bedre enn countdowntimer

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
        lLaps = (EditText) findViewById(R.id.LapLengthEditText);

    }


    public void startRun(View view){
        Run run = createNewRun();
        Intent intent = new Intent(this, RunActivity.class);
        intent.putExtra("run", run);
        startActivity(intent);
    }

    private Run createNewRun() {
        Date date = null;
        double lengthOfLaps = Double.parseDouble(String.valueOf(lLaps.getText()));
        int numberOfLaps = Integer.parseInt(String.valueOf(nLaps.getText()));
        try {
            date = d.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            
        }
        Run run = new Run(date, lengthOfLaps, numberOfLaps);
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