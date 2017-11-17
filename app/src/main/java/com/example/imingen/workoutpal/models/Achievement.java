package com.example.imingen.workoutpal.models;

import com.example.imingen.workoutpal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marius on 16.10.2017.
 */

public class Achievement {

    public static ArrayList<Achievement> achievements = new ArrayList<>();

    private String achievementName;
    private String achievementDescription;


    private int achievementDifficulty;
    private int logoId;

    public Achievement(){}

    public Achievement(String achievementName, String achievementDescription, int difficulty) {
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.achievementDifficulty = difficulty;
        achievements.add(this);
    }

    public static List<Achievement> achievementExampleData() {
        List<Achievement> ach = new ArrayList<>();
        String[] names = {"Level 0", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7",
                        "Level 8","Level 9","Level 10"};

        String[] descriptions = {"Completed 1 run", "Completed 10 runs", "Completed 25 runs", "Completed 50 runs",
                "Completed 100 runs", "Completed 200 runs", "Completed 300 runs", "Completed 500 runs",
                "Completed 750 runs","Completed 1000 runs","Completed 2000 runs"};

        int[] difficulties = {1,1,1,2,2,3,3,4,4,5,5};

        int[] logos = {R.drawable.trophy};

        for(int i = 0; i < names.length; i++){
            Achievement a = new Achievement(names[i], descriptions[i], difficulties[i]);
            a.setLogoId(logos[0]);
            ach.add(a);
        }
        return ach;
    }


    public String getAchievementDescription() {
        return achievementDescription;
    }

    public void setAchievementDescription(String achievementDescription) {
        this.achievementDescription = achievementDescription;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }



    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public int getAchievementDifficulty() {
        return achievementDifficulty;
    }

    public void setAchievementDifficulty(int achievementDifficulty) {
        this.achievementDifficulty = achievementDifficulty;
    }

}
