package com.example.imingen.workoutpal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Marius on 15.10.2017.
 */

public class Run implements Parcelable{


    private double timeRunning;
    private double distance;
    private double lapLength;
    private int numberOfLaps;
    private Date dateOfRun;


    public Run(Date date, double laplen, int lapnum) {
        this.dateOfRun = date;
        this.lapLength = laplen;
        this.numberOfLaps = lapnum;
    }

    public static List<Run> runExampleData(){
        double randomValue;
        double randomDistance;
        Date d;
        List<Run> run = new ArrayList<>();

        for(int i = 0; i < 45; i++){
            Random r = new Random();
            d = new Date(Math.abs(System.currentTimeMillis() - r.nextLong()));
            randomValue = 10 + (90 - 10) * r.nextDouble();
            randomDistance = 1 + (25 - 10) * r.nextDouble();

            Run current = new Run(d, 2, 3);
            current.setDistance(randomDistance);
            current.setTimeRunning(randomValue);
            run.add(current);
        }
        return run;
    }


    public void startRun() {

    }

    public void stopRun() {

    }

    public void pauseRun(){

    }

    // region getters and setters
    public double getLapLength() {
        return lapLength;
    }

    public void setLapLength(double lapLength) {
        this.lapLength = lapLength;
    }

    public int getNumberOfLaps() {
        return numberOfLaps;
    }

    public void setNumberOfLaps(int numberOfLaps) {
        this.numberOfLaps = numberOfLaps;
    }

    public double getTimeRunning() {
        return timeRunning;
    }

    public void setTimeRunning(double timeRunning) {
        this.timeRunning = timeRunning;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public Date getDateOfRun() {
        return dateOfRun;
    }

    public void setDateOfRun(Date dateOfRun) {
        this.dateOfRun = dateOfRun;
    }
    // endregion

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.dateOfRun.getTime());
        parcel.writeDouble(this.getLapLength());
        parcel.writeInt(this.getNumberOfLaps());
    }


    public static final Parcelable.Creator<Run> CREATOR = new Parcelable.Creator<Run>() {
        @Override
        public Run createFromParcel(Parcel source) {
            Date date = new Date(source.readLong());
            double len = source.readDouble();
            int num = source.readInt();
            return new Run(date, len, num);
        }

        @Override
        public Run[] newArray(int size) {
            return new Run[size];
        }
    };
}
