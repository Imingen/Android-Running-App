package com.example.imingen.workoutpal.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Marius on 15.10.2017.
 */

public class Run {


    private double timeRunning;
    private double distance;


    public Run(double timeRunning, double distance) {
        this.timeRunning = timeRunning;
        this.distance = distance;
    }

    public static List<Run> runExampleData(){
        double randomValue;
        double randomDistance;
        List<Run> run = new ArrayList<>();

        for(int i = 0; i < 45; i++){
            Random r = new Random();
            randomValue = 10 + (90 - 10) * r.nextDouble();
            randomDistance = 1 + (25 - 10) * r.nextDouble();

            Run current = new Run(randomValue, randomDistance);
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

    public double getTimeRunning() {
        return timeRunning;
    }

    public double getDistance() {
        return distance;
    }
}
