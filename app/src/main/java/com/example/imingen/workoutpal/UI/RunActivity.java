package com.example.imingen.workoutpal.UI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.fragments.NavigationDrawerFragment;
import com.example.imingen.workoutpal.models.Run;

public class RunActivity extends AppCompatActivity {

    Run run;
    TextView numLaps;
    TextView lenLaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        Bundle data = getIntent().getExtras();
        run = data.getParcelable("run");

        numLaps = (TextView) findViewById(R.id.numbeOfLaps);
        lenLaps = (TextView) findViewById(R.id.lengthPerLap);

        numLaps.setText(String.valueOf(run.getNumberOfLaps()));
        lenLaps.setText(String.valueOf(run.getLapLength()));

    }
}
