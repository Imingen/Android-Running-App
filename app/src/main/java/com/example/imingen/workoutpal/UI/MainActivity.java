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
import android.view.View;
import android.widget.TextView;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.fragments.NavigationDrawerFragment;

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

    //ToDo: Add more info to RUN.class. Se detaljert prosjektbeskrivelse. Skal egentlig være en abstrakt klasse. Må hvertfall legge til intervall info
    //ToDo: Fragment når man trykker på start knappen --> fragment skal kunne velge hvor lang intervall --> gå til selve run aktiviteten
    //ToDo: I den nye aktiviteten; Implementer logikk for å starte, stoppe og pause et run. Kunne ta tid som minimum
    // TODO: Implementer riktig backstack

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpDrawer();

        TextView dateTW = (TextView) findViewById(R.id.dateEditText);
        dateTW.setText(getCurrentDate());
    }


    public void startRun(View view){
        Intent intent = new Intent(this, RunActivity.class);
        startActivity(intent);
    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_main);
    }

    private String getCurrentDate() {
        long date = System.currentTimeMillis();
        SimpleDateFormat d = new SimpleDateFormat("E MMM dd, yyyy");
        String dateString = d.format(date);
        return dateString;
    }

    @Override
    protected void onStart() {
        navigationDrawerFragment.updateCheckedItem(R.id.nav_main);
        super.onStart();
    }



}