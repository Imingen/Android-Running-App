package com.example.imingen.workoutpal.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.imingen.workoutpal.R;

public class AchievementDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_details);

        Bundle achievementAttr = getIntent().getExtras();
        String title = achievementAttr.getString("Title");
        String description = achievementAttr.getString("Description");
        int difficulty = achievementAttr.getInt("Difficulty");

        TextView titleView = findViewById(R.id.titleTextView);
        TextView descriptionView = findViewById(R.id.descriptionTextView);

        RatingBar difficultyBar = findViewById(R.id.ratingBar);

        titleView.setText(title);
        descriptionView.setText(description);
        difficultyBar.setRating(difficulty);
    }
}
