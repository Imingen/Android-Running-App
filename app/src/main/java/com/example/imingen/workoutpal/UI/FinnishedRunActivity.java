package com.example.imingen.workoutpal.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.fragments.FinishedRunFragment;

public class FinnishedRunActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_finnished_run);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FinishedRunFragment finishedRunFragment = new FinishedRunFragment();
        fragmentTransaction.replace(android.R.id.content, finishedRunFragment);
        fragmentTransaction.commit();


    }
}
