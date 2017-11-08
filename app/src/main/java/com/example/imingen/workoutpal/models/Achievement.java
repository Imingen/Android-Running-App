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
    private int logoId;

    public Achievement(){}

    public Achievement(String achievementName, String achievementDescription) {
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        achievements.add(this);
    }

    public static List<Achievement> achievementExampleData() {
        List<Achievement> ach = new ArrayList<>();
        String[] names = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7",
                        "Level 8","Level 9","Level 10",};

        String[] descriptions = {"Completed 10 runs", "Completed 25 runs", "Completed 50 runs",
                "Completed 100 runs", "Completed 200 runs", "Completed 300 runs", "Completed 500 runs",
                "Completed 750 runs","Completed 1000 runs","Completed 2000 runs",};

        int[] logos = {R.drawable.trophy};

        for(int i = 0; i < names.length; i++){
            Achievement a = new Achievement(names[i], descriptions[i]);
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
}
