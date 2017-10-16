package com.example.imingen.workoutpal.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.adapter.HistoryTabAdapter;
import com.example.imingen.workoutpal.models.Session;

import java.util.List;

/**
 * Created by Marius on 16.10.2017.
 */

public class HistoryTabFragment extends Fragment {

    private List<Session> sessionList = Session.sessionExampleData();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public HistoryTabFragment() {
        //Empty required constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historytab, container, false);
        recyclerView = view.findViewById(R.id.history_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryTabAdapter(sessionList);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
