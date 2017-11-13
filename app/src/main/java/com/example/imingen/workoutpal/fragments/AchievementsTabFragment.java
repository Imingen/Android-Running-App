package com.example.imingen.workoutpal.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.adapter.AchievementTabAdapter;
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

public class AchievementsTabFragment extends Fragment {

    private List<Achievement> achlist = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private FirebaseAuth firebaseAuth;

    public AchievementsTabFragment() {
        //Empty required constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).child("Achievements");

        View view = inflater.inflate(R.layout.fragment_achievements, container, false);
        recyclerView = view.findViewById(R.id.achievement_recycler_view);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AchievementTabAdapter(achlist);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Achievement ach = dataSnapshot.getValue(Achievement.class);
                achlist.add(ach);
                adapter.notifyItemInserted(achlist.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Achievement ach = dataSnapshot.getValue(Achievement.class);
                achlist.add(ach);
                adapter.notifyItemInserted(achlist.size());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                List<Achievement> achlist2 = achlist;
                adapter = new AchievementTabAdapter(achlist2);
                achlist.clear();
                Achievement ach = dataSnapshot.getValue(Achievement.class);
                achlist.add(ach);
                adapter.notifyItemInserted(achlist.size());
                adapter = new AchievementTabAdapter(achlist);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addChildEventListener(childEventListener);
    }


}
