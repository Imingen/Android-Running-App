package com.example.imingen.workoutpal.UI;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.fragments.CountdownFragment;
import com.example.imingen.workoutpal.fragments.NavigationDrawerFragment;
import com.example.imingen.workoutpal.models.Run;
import com.example.imingen.workoutpal.models.RunTimer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class RunActivity extends AppCompatActivity {

    int ok;
    Run run;
    TextView timerTextView;
    TextView runOrPause;
    TextView lapsLefTextView;
    Button runButton;
    int numberOfLaps;
    TextToSpeech textToSpeech;
    Handler handler;
    FragmentManager fragmentManager;
    CountdownFragment countdownFragment;
    //Timer logic
    int minutes;
    int seconds;
    String minuteString;
    String secondString;
    int totalSeconds;
    boolean timeUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        Bundle data = getIntent().getExtras();
        run = data.getParcelable("run");

        handler = new Handler();

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        runOrPause = (TextView) findViewById(R.id.runOrPauseTextView);
        runButton = (Button) findViewById(R.id.start_run);
        lapsLefTextView = (TextView) findViewById(R.id.lapsLeftTextView);

        minutes = run.getLengthMinutes();
        seconds = run.getLengthSeconds();
        numberOfLaps = run.getNumberOfLaps();
        numberOfLaps = numberOfLaps * 2 ;

        updateTimer(minutes, seconds);
        lapsLefTextView.setText(Integer.toString(numberOfLaps / 2));

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.ERROR){
                    Toast.makeText(getApplicationContext(), "Text to speech not supported on your device",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    textToSpeech.setLanguage(Locale.ENGLISH);
                    textToSpeech.setSpeechRate(0.9f);
                }
            }
        });
    }


    public void startRun(View view) {
        runButton.setEnabled(false);
        //Have to have the time in only seconds for easier checking
        totalSeconds = seconds + (minutes * 60);
        //Keep the inital value of totalseconds so that we can reset the timer every lap since both laps and pauses are the same length
        final int totalSecondsMemory = totalSeconds;
        timeUp = false;
        Runnable run = new Runnable() {
            @Override
            public void run() {
                //Casting to int will round it down
                int minutes = (int) totalSeconds / 60;
                int seconds = totalSeconds - minutes * 60;

//                //If the timer is up and there are no more laps left
//                if(totalSeconds < 0 && numberOfLaps == 0){
//                    timerTextView.setText("00:00");
//                    timeUp = true;
//                }
                //If total seconds are up but there are more laps left, reset the timer by adding
                //resetting totalseconds

                //Updates the timer
                if(timeUp == false){
                    if(totalSeconds == 0 && numberOfLaps > 0){
                        timerTextView.setText("00:00");
                        numberOfLaps--;
                        totalSeconds = totalSecondsMemory + 1;
                        if((numberOfLaps % 2) != 0 && numberOfLaps != 1){
                            textToSpeech("Pause for" + Integer.toString(totalSecondsMemory) + " seconds");
                        }
                    }
                    if((numberOfLaps % 2) == 0){
                        if(totalSeconds <= 3){
                            textToSpeech(Integer.toString(totalSeconds));
                        }
                        runOrPause.setText("RUN!");
                    }

                    if((numberOfLaps % 2) != 0 && numberOfLaps != 1) {
                        if(totalSeconds == 4){
                            textToSpeech("Starting in");
                        }
                        if(totalSeconds <= 3){
                            textToSpeech(Integer.toString(totalSeconds));
                        }
                        runOrPause.setText("PAUSE!");
                        lapsLefTextView.setText(Integer.toString(numberOfLaps / 2));
                    }
                    updateTimer(minutes, seconds);
                    handler.postDelayed(this, 1000);

                    if(numberOfLaps == 1){
                        timeUp = true;
                    }
                }
                //Countdown the totalseconds, makes it easier
                totalSeconds--;
                if(timeUp == true){
                    handler.removeCallbacks(this);
                    Intent intent = new Intent(RunActivity.this, FinnishedRunActivity.class);
                    startActivity(intent);
                }
            }
        };
        handler.post(run);
    }



    public void textToSpeech(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
        }
    }

    //Tatt fra https://stackoverflow.com/questions/27968146/texttospeech-with-api-21
    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId=this.hashCode() + "";
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
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
