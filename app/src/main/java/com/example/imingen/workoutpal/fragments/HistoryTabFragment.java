package com.example.imingen.workoutpal.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.adapter.HistoryTabAdapter;
import com.example.imingen.workoutpal.models.Achievement;
import com.example.imingen.workoutpal.models.Run;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marius on 16.10.2017.
 */

public class HistoryTabFragment extends Fragment {



    private List<Run> runList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference2;
    private ChildEventListener childEventListener2;

    private Achievement ach1;
    private Achievement ach2;
    private Achievement ach3;
    private Achievement ach4;
    private Achievement ach5;

    public HistoryTabFragment() {
        //Empty required constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).child("Runs");
        databaseReference2 = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).child("Achievements");
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Run run = dataSnapshot.getValue(Run.class);
                runList.add(0, run);
                adapter.notifyItemInserted(runList.size()-1);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);


        View view = inflater.inflate(R.layout.fragment_historytab, container, false);
        recyclerView = view.findViewById(R.id.history_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryTabAdapter(runList);
        recyclerView.setAdapter(adapter);

        return view;
    }



}
