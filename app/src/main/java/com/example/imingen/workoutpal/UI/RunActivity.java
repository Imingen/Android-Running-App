package com.example.imingen.workoutpal.UI;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.Service.MyService;
import com.example.imingen.workoutpal.helpers.NotificationHelper;
import com.example.imingen.workoutpal.models.Run;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

public class RunActivity extends AppCompatActivity{

    Run run;
    TextView timerTextView;
    TextView runOrPause;
    TextView lapsLefTextView;
    Button runButton;
    int numberOfLaps;
    TextToSpeech textToSpeech;
    Handler handler;
    Runnable runnable;
    Switch aSwitch;

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
    private Locale locale;

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

        aSwitch = findViewById(R.id.mutedSwitch);

        minutes = run.getLengthMinutes();
        seconds = run.getLengthSeconds();
        numberOfLaps = run.getNumberOfLaps();
        //times two for equal amounts of pause and runs
        numberOfLaps = numberOfLaps * 2 ;

        updateTimer(minutes, seconds);
        //Sets the number of laps left to just the amount of laps supposed to be ran
        lapsLefTextView.setText(Integer.toString(numberOfLaps / 2));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Gets the language code and the region code of the language. Cant make new functional
        //Locale based on just language
        String [] voice = prefs.getString("languages", "").split("_");

        //This means that a language is chosen in settings, to prevent nullpointer
        if(voice.length > 1){
            String language = voice[0];
            String region = voice[1];
            locale = new Locale(language, region);
            locale.setDefault(locale);
        }
        //If not language is set from the settings within the app, then set the language to english
        else{
            locale = Locale.ENGLISH;
        }

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.ERROR){
                    Toast.makeText(getApplicationContext(), "Text to speech not supported on your device",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    textToSpeech.setLanguage(locale);
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

    /**
     * Method is called startRun2 because a startRun1 used to exist, but what was put down for lack of usefulness to the greater good.
     * This method works with the totalseconds that are counted down in background service and updated GUI accordingly
     * and gives TTS somewhat correct instructions.
     */
    public void startRun2(){
        handler = new Handler();
        //To keep track of the inital value of total seconds so it can easily be re-adjusted when one lap is over
        final int totalSecondsMemory = totalSeconds;
        timeUp = false;
        runnable = new Runnable() {
            @Override
            public void run() {
                Boolean mute = aSwitch.isChecked();
                //Gets the current totalseconds from service, that counts down in background
                int total = myService.countDownSeconds();
                int minutes = total / 60;
                int seconds = total - minutes * 60;

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
                        if((numberOfLaps % 2) != 0 && numberOfLaps != 1 && !mute){
                            textToSpeech("Pause for" + Integer.toString(totalSecondsMemory) + " seconds");
                        }
                    }
                    if((numberOfLaps % 2) == 0){
                        if(total <= 3 && !mute){
                            textToSpeech(Integer.toString(total));
                        }
                        runOrPause.setText("RUN!");
                    }
                    //If number of laps left is odd, then it is currently a pause in your running
                    if((numberOfLaps % 2) != 0 && numberOfLaps != 1) {
                        if(total == 4 && !mute){
                            textToSpeech("Starting in");
                        }
                        if(total <= 3 && !mute){
                            textToSpeech(Integer.toString(total));
                        }
                        runOrPause.setText("PAUSE!");
                        lapsLefTextView.setText(Integer.toString(numberOfLaps / 2));
                    }
                    updateTimer(minutes, seconds);
                    handler.postDelayed(this, 1000);

                    //If number of laps left is one, that means it is a pause and since it is the last
                    //we dont want to count or do anything here but rather finish up the workout
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

    //Makes sure the notification is also cancelled on backpressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        notificationHelper.getNotificationManager().cancel(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        unbindService(connection);
        this.stopService(new Intent(this, MyService.class));
    }

    /**
     * Pushes the completed run to the database, cancels notification and starts the next activity
     */
    public void finnishedRun(){
        databaseReference.push().setValue(run);
        //Cancels the current notification
        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        notificationHelper.getNotificationManager().cancel(1);

        Intent intent = new Intent(RunActivity.this, FinnishedRunActivity.class);
        startActivity(intent);
    }

    //The next three (3) methods are taken from https://stackoverflow.com/questions/27968146/texttospeech-with-api-21
    public void textToSpeech(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
        }
    }

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
