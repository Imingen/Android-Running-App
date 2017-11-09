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

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int logos = R.drawable.trophy;
                        if(runList.size() == 1){
                            databaseReference2.removeValue();
                            String name = "Level 0";
                            String description = "Completed 1 run";
                            ach1 = new Achievement(name, description);
                            ach1.setLogoId(logos);
                            databaseReference2.push().setValue(ach1);
                        }
                        if(runList.size() == 2){
                            databaseReference2.removeValue();

                            String name = "Level 0";
                            String description = "Completed 1 run";
                            ach1 = new Achievement(name, description);
                            ach1.setLogoId(logos);
                            databaseReference2.push().setValue(ach1);

                            String name2 = "Level 1";
                            String description2 = "Completed 2 runs";
                            ach2 = new Achievement(name2, description2);
                            ach2.setLogoId(logos);
                            databaseReference2.push().setValue(ach2);
                        }
                        if(runList.size() == 3){

                            databaseReference2.removeValue();


                            String name = "Level 0";
                            String description = "Completed 1 run";
                            ach1 = new Achievement(name, description);
                            ach1.setLogoId(logos);
                            databaseReference2.push().setValue(ach1);

                            String name2 = "Level 1";
                            String description2 = "Completed 2 runs";
                            ach2 = new Achievement(name2, description2);
                            ach2.setLogoId(logos);
                            databaseReference2.push().setValue(ach2);

                            String name3 = "Level 2";
                            String description3 = "Completed 3 runs";
                            ach3 = new Achievement(name3, description3);
                            ach3.setLogoId(logos);
                            databaseReference2.push().setValue(ach3);
                        }
                        if(runList.size() == 4){
                            databaseReference2.removeValue();

                            String name = "Level 0";
                            String description = "Completed 1 run";
                            ach1 = new Achievement(name, description);
                            ach1.setLogoId(logos);
                            databaseReference2.push().setValue(ach1);

                            String name2 = "Level 1";
                            String description2 = "Completed 2 runs";
                            ach2 = new Achievement(name2, description2);
                            ach2.setLogoId(logos);
                            databaseReference2.push().setValue(ach2);

                            String name3 = "Level 2";
                            String description3 = "Completed 3 runs";
                            ach3 = new Achievement(name3, description3);
                            ach3.setLogoId(logos);
                            databaseReference2.push().setValue(ach3);

                            String name4 = "Level 3";
                            String description4 = "Completed 4 runs";
                            ach4 = new Achievement(name4, description4);
                            ach4.setLogoId(logos);
                            databaseReference2.push().setValue(ach4);
                        }
                        if(runList.size() >= 5){
                            databaseReference2.removeValue();

                            String name = "Level 0";
                            String description = "Completed 1 run";
                            ach1 = new Achievement(name, description);
                            ach1.setLogoId(logos);
                            databaseReference2.push().setValue(ach1);

                            String name2 = "Level 1";
                            String description2 = "Completed 2 runs";
                            ach2 = new Achievement(name2, description2);
                            ach2.setLogoId(logos);
                            databaseReference2.push().setValue(ach2);

                            String name3 = "Level 2";
                            String description3 = "Completed 3 runs";
                            ach3 = new Achievement(name3, description3);
                            ach3.setLogoId(logos);
                            databaseReference2.push().setValue(ach3);

                            String name4 = "Level 3";
                            String description4 = "Completed 4 runs";
                            ach4 = new Achievement(name4, description4);
                            ach4.setLogoId(logos);
                            databaseReference2.push().setValue(ach4);

                            String name5 = "Level 4";
                            String description5 = "Completed 5 runs";
                            ach5 = new Achievement(name5, description5);
                            ach5.setLogoId(logos);
                            databaseReference2.push().setValue(ach5);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        childEventListener2 = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
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
        databaseReference2.addChildEventListener(childEventListener2);

        View view = inflater.inflate(R.layout.fragment_historytab, container, false);
        recyclerView = view.findViewById(R.id.history_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryTabAdapter(runList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public int getRunList() {
        return runList.size();
    }

}
