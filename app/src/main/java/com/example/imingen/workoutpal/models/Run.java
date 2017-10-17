package com.example.imingen.workoutpal.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Marius on 15.10.2017.
 */

public class Run {


    private double timeRunning;
    private double distance;
    private double lapLength;
    private int numberOfLaps;
    private Date dateOfRun;


    public Run(double timeRunning, double distance, Date date) {
        this.timeRunning = timeRunning;
        this.distance = distance;
        this.dateOfRun = date;
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

            Run current = new Run(randomValue, randomDistance, d);
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

    public double getDistance() {
        return distance;
    }

    public Date getDateOfRun() {
        return dateOfRun;
    }

    public void setDateOfRun(Date dateOfRun) {
        this.dateOfRun = dateOfRun;
    }
}
