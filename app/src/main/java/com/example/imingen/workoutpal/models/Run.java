package com.example.imingen.workoutpal.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Marius on 15.10.2017.
 */

public class Run implements Parcelable{



    private int numberOfLaps;

    private int lengthMinutes;
    private int lengthSeconds;
    private Date dateOfRun;


    public Run(){}

    public Run(Date date, int minutes, int seconds, int lapnum) {
        this.dateOfRun = date;
        this.lengthMinutes = minutes;
        this.lengthSeconds = seconds;
        this.numberOfLaps = lapnum;
    }


    public void startRun() {

    }

    public void stopRun() {

    }

    public void pauseRun(){

    }

    // region getters and setters



    public int getNumberOfLaps() {
        return numberOfLaps;
    }

    public void setNumberOfLaps(int numberOfLaps) {
        this.numberOfLaps = numberOfLaps;
    }

    public Date getDateOfRun() {
        return dateOfRun;
    }

    public void setDateOfRun(Date dateOfRun) {
        this.dateOfRun = dateOfRun;
    }

    public int getLengthMinutes() {
        return lengthMinutes;
    }

    public void setLengthMinutes(int lengthMinutes) {
        this.lengthMinutes = lengthMinutes;
    }

    public int getLengthSeconds() {
        return lengthSeconds;
    }

    public void setLengthSeconds(int lengthSeconds) {
        this.lengthSeconds = lengthSeconds;
    }

    @Exclude
    public String getTime(){
        int totalSeconds = lengthSeconds + (lengthMinutes * 60);
        int minutes = (int) totalSeconds / 60;
        int seconds = totalSeconds - minutes * 60;
        String minuteString = Integer.toString(minutes);
        String secondString = Integer.toString(seconds);

        if(seconds <= 9){
            secondString = "0" + seconds;
        }
        if(minutes <= 9){
            minuteString = "0" + minutes;
        }
        String timeString = minuteString + ":" + secondString;
        return timeString;
    }

    // endregion

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.dateOfRun.getTime());
        parcel.writeInt(this.getLengthMinutes());
        parcel.writeInt(this.getLengthSeconds());
        parcel.writeInt(this.getNumberOfLaps());
    }


    public static final Parcelable.Creator<Run> CREATOR = new Parcelable.Creator<Run>() {
        @Override
        public Run createFromParcel(Parcel source) {
            Date date = new Date(source.readLong());
            int minutes = source.readInt();
            int seconds = source.readInt();
            int num = source.readInt();
            return new Run(date, minutes, seconds, num);
        }

        @Override
        public Run[] newArray(int size) {
            return new Run[size];
        }
    };
}
