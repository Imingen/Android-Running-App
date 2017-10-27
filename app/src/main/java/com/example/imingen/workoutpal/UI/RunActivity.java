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
    TextView timerTextView;
    TextView runOrPause;
    int numberOfLaps;

    //Timer logic
    int minutes;
    int seconds;
    String minuteString;
    String secondString;
    int totalSeconds;
    boolean timeUp;
    boolean pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        Bundle data = getIntent().getExtras();
        run = data.getParcelable("run");

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        runOrPause = (TextView) findViewById(R.id.runOrPauseTextView);

        minutes = run.getLengthMinutes();
        seconds = run.getLengthSeconds();
        numberOfLaps = run.getNumberOfLaps();
        numberOfLaps = numberOfLaps * 2;
        updateTimer(minutes, seconds);
    }


    public void startRun(View view) {
        //Take minus one lap when button is first pressed

        //Have to have the time in only seconds for easier checking
        totalSeconds = seconds + (minutes * 60);
        //Keep the inital value of totalseconds so that we can reset the timer every lap since all laps are the same length
        final int totalSecondsMemory = totalSeconds;
        timeUp = false;
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                //Countdown the totalseconds, makes it easier
                totalSeconds--;
                //Casting to int will round it down
                int minutes = (int) totalSeconds / 60;
                int seconds = totalSeconds - minutes * 60;

                //If the timer is up and there are no more laps left
                if(totalSeconds < 0 && numberOfLaps == 0){
                    timerTextView.setText("00:00");
                    timeUp = true;
                }
                //If total seconds are up but there are more laps left, reset the timer by adding
                //resetting totalseconds
                else if(totalSeconds == 0 && numberOfLaps > 0){
                    timerTextView.setText("00:00");
                    numberOfLaps--;
                    totalSeconds = totalSecondsMemory + 1;
                }
                //Updates the timer
                if(timeUp == false){
                    Log.i("Number of laps", Integer.toString(numberOfLaps));
                    if((numberOfLaps % 2) == 0){
                        runOrPause.setText("RUN!");
                        updateTimer(minutes, seconds);
                        handler.postDelayed(this, 1000);
                    }
                    else if((numberOfLaps % 2) != 0) {
                        runOrPause.setText("PAUSE!");
                        updateTimer(minutes, seconds);
                        handler.postDelayed(this, 1000);
                    }

                }
            }
        };
        handler.post(run);
    }




    /**
     * Helper method for updating the timertextview
     * @param minutes
     * @param seconds
     */
    public void updateTimer(int minutes, int seconds){
        minuteString = Integer.toString(minutes);
        secondString = Integer.toString(seconds);

        //Making the timer aestethically pleasing by making sure there is always two digits
        if(seconds <= 9){
            secondString = "0" + seconds;
        }
        if(minutes <= 9){
            minuteString = "0" + minutes;
        }
        timerTextView.setText(minuteString + ":" + secondString );
    }

}
