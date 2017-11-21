package com.example.imingen.workoutpal.helpers;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.UI.RunActivity;

/**
 * Created by Mingen on 21-Nov-17.
 */

public class NotificationHelper extends ContextWrapper {

    public static final String CHANNEL_ID = "com.example.imingen.workoutpal";
    public static final String CHANNEL_NAME = "WorkoutPal";

    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel(){
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getNotificationManager().createNotificationChannel(channel);
    }


    public NotificationManager getNotificationManager(){
        if(notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message){
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(this, RunActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        return  new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_runner)
                .setContentIntent(pendingIntent);
    }

}
