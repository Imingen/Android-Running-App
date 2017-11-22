package com.example.imingen.workoutpal.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
