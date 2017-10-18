package com.example.imingen.workoutpal.UI;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.fragments.NavigationDrawerFragment;
import com.example.imingen.workoutpal.models.Run;
import com.example.imingen.workoutpal.models.RunTimer;

public class RunActivity extends AppCompatActivity {

    Run run;
    TextView numLaps;
    TextView lenLaps;
    TextView timerTextView;
    //Timer logic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        Bundle data = getIntent().getExtras();
        run = data.getParcelable("run");

        numLaps = (TextView) findViewById(R.id.numbeOfLaps);
        lenLaps = (TextView) findViewById(R.id.lengthPerLap);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        numLaps.setText(String.valueOf(run.getNumberOfLaps()));
        lenLaps.setText(String.valueOf(run.getLapLength()));


    }


    public void startRun(View view) {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                timerTextView.setText("done!");
            }

        }.start();
    }


}
