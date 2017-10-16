package com.example.imingen.workoutpal.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.models.Session;

public class AchievementsFragment extends Fragment {

    public AchievementsFragment() {
        //Empty required constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_achievements, container, false);

        return view;
    }
}
