package com.example.imingen.workoutpal.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {

    private final MyLocalBinder myBinder = new MyLocalBinder();

    private int totalSeconds;
    private int numLaps;
    private int secondsMem;
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
        return  totalSeconds;
    }
}
