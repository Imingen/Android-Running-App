package com.example.imingen.workoutpal.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.imingen.workoutpal.helpers.NotificationHelper;

public class MyService extends Service {

    private final MyLocalBinder myBinder = new MyLocalBinder();

    private int totalSeconds;
    private int numLaps;
    private int secondsMem;

    private static final String NOTIFICATION_TITLE = "Exercise in progress, keep it up!";
    private String notificationMessage;

    private NotificationHelper notificationHelper;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;

    public MyService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        totalSeconds = bundle.getInt("seconds");
        numLaps = bundle.getInt("numLaps");
        secondsMem = totalSeconds;
        notificationHelper = new NotificationHelper(getApplicationContext());
        notificationMessage = "Timer " + Integer.toString(totalSeconds);
        startNotification(NOTIFICATION_TITLE, notificationMessage);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class MyLocalBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    public int countDownSeconds(){
        if (numLaps > 0){
            if(totalSeconds == 0){
                totalSeconds = secondsMem;
                numLaps--;
            }
            else{
                totalSeconds--;
            }
        }
        Log.i("XD5", Integer.toString(totalSeconds));
        updateNotification(NOTIFICATION_TITLE, "Timer " + Integer.toString(totalSeconds), secondsMem, totalSeconds);
        return  totalSeconds;
    }


    public void startNotification(String title, String message){
        builder = notificationHelper.getChannelNotification(title, message);
        notificationManager = notificationHelper.getNotificationManager();
        notificationManager.notify(1, builder.build());
    }

    public void updateNotification(String title, String message, int max, int current){
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setProgress(secondsMem, totalSeconds, false );
        notificationManager.notify(1, builder.build());
    }

}
