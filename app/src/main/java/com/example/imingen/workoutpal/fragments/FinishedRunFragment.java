package com.example.imingen.workoutpal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imingen.workoutpal.R;

/**
 * Created by Mingen on 01-Nov-17.
 */

public class FinishedRunFragment extends Fragment {

    TextView congratz;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finished_run, container, false);
        congratz = view.findViewById(R.id.congratsTextView);
        congratz.setText("Congratulations \n you finished todays workout");
        congratz.setGravity(Gravity.CENTER_HORIZONTAL);
        return view;
    }
}