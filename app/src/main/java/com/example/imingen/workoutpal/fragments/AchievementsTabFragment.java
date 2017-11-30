package com.example.imingen.workoutpal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.UI.AchievementDetailsActivity;
import com.example.imingen.workoutpal.adapter.AchievementTabAdapter;
import com.example.imingen.workoutpal.models.Achievement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AchievementsTabFragment extends Fragment {

    private List<Achievement> achlist = new ArrayList<>();
    private ValueEventListener updateUIWithAchievements;

    private RecyclerView recyclerView;
    private AchievementTabAdapter adapter;
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
        setHasOptionsMenu(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).child("Achievements");
        databaseReference.keepSynced(true);

        updateUIWithAchievements = new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Achievement achievement = snapshot.getValue(Achievement.class);
                    achlist.add(achievement);

                    adapter.notifyItemInserted(achlist.size());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        databaseReference.addValueEventListener(updateUIWithAchievements);

        View view = inflater.inflate(R.layout.fragment_achievements, container, false);
        recyclerView = view.findViewById(R.id.achievement_recycler_view);
        layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AchievementTabAdapter(achlist);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AchievementTabAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getContext(), AchievementDetailsActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), recyclerView.getChildAt(position), ViewCompat.getTransitionName(recyclerView));
                Bundle bundle = new Bundle();
                bundle.putString("Title", achlist.get(position).getAchievementName());
                bundle.putString("Description", achlist.get(position).getAchievementDescription());
                bundle.putInt("Difficulty", achlist.get(position).getAchievementDifficulty());
                intent.putExtras(bundle);
                startActivity(intent, optionsCompat.toBundle());
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_signout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}