package com.example.imingen.workoutpal.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imingen.workoutpal.R;

/**
 * Created by Marius on 16.10.2017.
 */

public class HistoryTabFragment extends Fragment {

    public HistoryTabFragment() {
        //Empty required constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historytab, container, false);

        return view;
    }
}
