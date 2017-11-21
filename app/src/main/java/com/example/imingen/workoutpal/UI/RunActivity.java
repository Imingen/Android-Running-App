package com.example.imingen.workoutpal.UI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.Service.MyService;
import com.example.imingen.workoutpal.fragments.CountdownFragment;
import com.example.imingen.workoutpal.fragments.HistoryTabFragment;
import com.example.imingen.workoutpal.fragments.NavigationDrawerFragment;
import com.example.imingen.workoutpal.helpers.NotificationHelper;
import com.example.imingen.workoutpal.models.Achievement;
import com.example.imingen.workoutpal.models.Run;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class RunActivity extends AppCompatActivity{

    int ok;
    Run run;
    TextView timerTextView;
    TextView runOrPause;
    TextView lapsLefTextView;
    Button runButton;
    Switch aSwitch;
    int numberOfLaps;
    TextToSpeech textToSpeech;
    Handler handler;
    Runnable runnable;
    FragmentManager fragmentManager;
    CountdownFragment countdownFragment;
    //Timer logic
    int minutes;
    int seconds;
    String minuteString;
    String secondString;
    int totalSeconds;
    boolean timeUp;

    //Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference2;

    private Button startRunButton;

    private boolean isBound = false;
    private MyService myService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.MyLocalBinder myBinder = (MyService.MyLocalBinder) iBinder;
            myService = myBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).child("Runs");
        databaseReference2 = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).child("Achievements");

        Bundle data = getIntent().getExtras();
        run = data.getParcelable("run");

        handler = new Handler();

        timerTextView = findViewById(R.id.timerTextView);
        runOrPause = findViewById(R.id.runOrPauseTextView);
        runButton = findViewById(R.id.start_run);
        lapsLefTextView = findViewById(R.id.lapsLeftTextView);

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
                else {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                    textToSpeech.setSpeechRate(0.9f);
                }
            }
        });

        totalSeconds = seconds + (minutes * 60);
        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putInt("seconds", totalSeconds);
        bundle.putInt("numLaps", numberOfLaps);
        intent.putExtras(bundle);
        startService(intent);

        startRunButton = findViewById(R.id.start_run);
        startRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRun2();
            }
        });



    }

    //Send all info to the service when bound
    // Bind the service in and onstart and onresume
    //start the service in oncreate
    // start the method that run the update of watch and talks to the user when pressing a button
    // if i understand correctly i only need to update the UI from the serivce. it is to make sure
    // that the clock and voice dontr stop when changing apps, e.g to a music app cuz who doesnt want to listen to music while runnign

//    public void startRun(View view) {
//        runButton.setEnabled(false);
////        //Have to have the time in only seconds for easier checking
//        totalSeconds = seconds + (minutes * 60);
////        //Keep the inital value of totalseconds so that we can reset the timer every lap since both laps and pauses are the same length
//        final int totalSecondsMemory = totalSeconds;
//        timeUp = false;
//        runnable = new Runnable() {
//            @Override
//            public void run() {
////                //Casting to int will round it down
//                int minutes = (int) totalSeconds / 60;
//                int seconds = totalSeconds - minutes * 60;
////
//////            //If the timer is up and there are no more laps left
//                if(totalSeconds < 0 && numberOfLaps == 0){
//                    timerTextView.setText("00:00");
//                    timeUp = true;
//                }
////                //If total seconds are up but there are more laps left, reset the timer by adding
////                //resetting totalseconds
////
////                //Updates the timer
//                if(timeUp == false){
//                    if(totalSeconds == 0 && numberOfLaps > 0){
//                        timerTextView.setText("00:00");
//                        numberOfLaps--;
//                        totalSeconds = totalSecondsMemory + 1;
//                        if((numberOfLaps % 2) != 0 && numberOfLaps != 1){
//                            textToSpeech("Pause for" + Integer.toString(totalSecondsMemory) + " seconds");
//                        }
//                    }
//                    if((numberOfLaps % 2) == 0){
//                        if(totalSeconds <= 3){
//                            textToSpeech(Integer.toString(totalSeconds));
//                        }
//                        runOrPause.setText("RUN!");
//                    }
//
//                    if((numberOfLaps % 2) != 0 && numberOfLaps != 1) {
//                        if(totalSeconds == 4){
//                            textToSpeech("Starting in");
//                        }
//                        if(totalSeconds <= 3){
//                            textToSpeech(Integer.toString(totalSeconds));
//                        }
//                        runOrPause.setText("PAUSE!");
//                        lapsLefTextView.setText(Integer.toString(numberOfLaps / 2));
//                    }
//                    updateTimer(minutes, seconds);
//                    handler.postDelayed(this, 1000);
//
//                    if(numberOfLaps == 1){
//                        timeUp = true;
//                    }
//                }
//                //Countdown the totalseconds, makes it easier
//                totalSeconds--;
//                if(timeUp == true){
//                    handler.removeCallbacks(this);
//                    finnishedRun();
//                }
//            }
//        };
//        handler.post(runnable);
//    }

    public void startRun2(){
        handler = new Handler();
        final int totalSecondsMemory = totalSeconds;
        timeUp = false;
        runnable = new Runnable() {
            @Override
            public void run() {
                int total = myService.countDownSeconds();
                int minutes = total / 60;
                int seconds = total - minutes * 60;
                Log.i("XD6", Integer.toString(total));

                 //If the timer is up and there are no more laps left
                if(total < 0 && numberOfLaps == 0){
                    timerTextView.setText("00:00");
                    timeUp = true;
                }
                if(!timeUp){
                    if(total == 0 && numberOfLaps > 0){
                        timerTextView.setText("00:00");
                        numberOfLaps--;
                        total = totalSecondsMemory + 1;
                        if((numberOfLaps % 2) != 0 && numberOfLaps != 1){
                            textToSpeech("Pause for" + Integer.toString(totalSecondsMemory) + " seconds");
                        }
                    }
                    if((numberOfLaps % 2) == 0){
                        if(total <= 3){
                            textToSpeech(Integer.toString(total));
                        }
                        runOrPause.setText("RUN!");
                    }

                    if((numberOfLaps % 2) != 0 && numberOfLaps != 1) {
                        if(total == 4){
                            textToSpeech("Starting in");
                        }
                        if(total <= 3){
                            textToSpeech(Integer.toString(total));
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
                if(timeUp == true){
                    handler.removeCallbacks(this);
                    finnishedRun();
                }

            }
        };
        handler.post(runnable);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        unbindService(connection);
        this.stopService(new Intent(this, MyService.class));
    }

    public void finnishedRun(){
        databaseReference.push().setValue(run);
        Intent intent = new Intent(RunActivity.this, FinnishedRunActivity.class);
        startActivity(intent);
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
