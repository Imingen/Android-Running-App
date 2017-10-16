package com.example.imingen.workoutpal.models;

/**
 * Created by Marius on 15.10.2017.
 */

public class Stats {

    private long timeRunning;
    private double distance;


    public Stats(long timeRunning, double distance) {
        this.timeRunning = timeRunning;
        this.distance = distance;
    }

    public long getTimeRunning() {
        return timeRunning;
    }

    public double getDistance() {
        return distance;
    }
}
