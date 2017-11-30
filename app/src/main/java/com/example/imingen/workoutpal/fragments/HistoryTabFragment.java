package com.example.imingen.workoutpal.fragments;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.adapter.HistoryTabAdapter;
import com.example.imingen.workoutpal.models.Run;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryTabFragment extends Fragment implements AdapterView.OnItemSelectedListener{

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

    public HistoryTabFragment() {
        //Empty required constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).child("Runs");
        //Wasnt sure if this was needed anymore so I commented it out, im to scared to delete it
//        databaseReference2 = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).child("Achievements");
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

        //The list to fill the the spiner with
        final String[] list = new String[]{
                "Sort by...",
                "Newest run",
                "Oldest run",
                "Most laps",
                "Longest lap"
        };

        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list){
           //Disables the first entry in the spinner so it looks like a placeholder text
            @Override
            public boolean isEnabled(int position) {
                if(position == 0){
                    return false;
                }
                else{
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the first entry in the spinner to the color gray for even more authentic placeholder look
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_signout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(i == 1){
                sortByNewest();
                adapter.notifyDataSetChanged();
            }
            if(i == 2){
                sortByOldest();
                adapter.notifyDataSetChanged();
            }

            if(i == 3){
                sortByMostLaps();
                adapter.notifyDataSetChanged();
            }

            if(i == 4){
                sortByLongestLap();

                adapter.notifyDataSetChanged();
            }
        }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void sortByNewest() {
        if (runList != null) {
            Collections.sort(runList, new Comparator<Run>() {
                @Override
                public int compare(Run run, Run t1) {
                    if (run.getDateOfRun().before(t1.getDateOfRun())) {
                        return 1;
                    } else if (run.getDateOfRun().after(t1.getDateOfRun())) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }
    }

    public void sortByOldest(){
        if (runList != null) {
            Collections.sort(runList, new Comparator<Run>() {
                @Override
                public int compare(Run run, Run t1) {
                    if (run.getDateOfRun().before(t1.getDateOfRun())) {
                        return -1;
                    } else if (run.getDateOfRun().after(t1.getDateOfRun())) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
        }
    }

    public void sortByMostLaps(){
        if(runList != null){
            Collections.sort(runList, new Comparator<Run>() {
                @Override
                public int compare(Run run, Run t1) {
                    return t1.getNumberOfLaps() - run.getNumberOfLaps();
                }
            });
        }
    }

    public void sortByLongestLap(){
        if (runList != null){
            Collections.sort(runList, new Comparator<Run>() {
                @Override
                public int compare(Run run, Run t1) {
                    int runtotal = run.getLengthSeconds() + (run.getLengthMinutes() * 60);
                    int t1total = t1.getLengthSeconds() + (t1.getLengthMinutes() * 60);

                    return t1total - runtotal;
                }
            });
        }
    }

}
