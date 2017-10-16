package com.example.imingen.workoutpal.models;

import com.example.imingen.workoutpal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marius on 16.10.2017.
 */

public class Achievement {

    private String achievementName;
    private Boolean completed;
    private int logoId;

    public static List<Achievement> achievementExampleData() {
        List<Achievement> ach = new ArrayList<>();
        String[] names = {"1K", "2K", "5K"};
        int[] logos = {R.drawable.trophy};
        Boolean[] completed = {true, true, false};

        for(int i = 0; i < names.length; i++){
            Achievement a = new Achievement();
            a.setAchievementName(names[i]);
            a.setCompleted(completed[i]);
            a.setLogoId(logos[0]);
            ach.add(a);
        }
        return ach;
    }


    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }
}
