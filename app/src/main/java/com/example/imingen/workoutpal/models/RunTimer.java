package com.example.imingen.workoutpal.models;

import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;

/**
 * Created by Mingen on 18-Oct-17.
 * Denne klassen er sterkt inspirert av:
 * https://stackoverflow.com/questions/33734074/how-do-i-make-a-simple-timer-in-java-with-a-button-click-to-start-stop-the-timer
 */

public class RunTimer {

    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    long timeSwapbuff = 0L;
    long updatedTime = 0L;
    private String time;
    private Handler handler = new Handler();


    public void StartTimer() {
        startTime = SystemClock.uptimeMillis();
        handler.removeCallbacks(runnable, 0);
    }

    public void StopTimer() {
        timeSwapbuff += timeInMilliseconds;
        handler.removeCallbacks(runnable);
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapbuff = timeInMilliseconds;

            int secs = (int) (timeInMilliseconds / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int hours = mins / 60;
            mins = mins % 60;
            time = "" + String.format("%02d", hours) + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs);
            handler.postDelayed(runnable, 1000);
        }
    };

    public String getTime() {
        return time;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public long getTimeSwapbuff() {
        return timeSwapbuff;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }
}
