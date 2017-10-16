package com.example.imingen.workoutpal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.imingen.workoutpal.HistoryActivity;
import com.example.imingen.workoutpal.MainActivity;
import com.example.imingen.workoutpal.R;

public class NavigationDrawerFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        navigationView = (NavigationView) view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        return view;
    }

    public void setUpDrawer(DrawerLayout drawerLayout, Toolbar toolbar, int menuItemId){
        this.drawerLayout = drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,  R.string.drawer_open, R.string.drawer_close);

        this.drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();

        navigationView.setCheckedItem(menuItemId);
    }

    public void updateCheckedItem(int menuItemId) {
        navigationView.setCheckedItem(menuItemId);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;

        switch (item.getItemId()){
            case R.id.nav_main:
                intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_history:
                intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
                break;
            //case R.id.nav_settings:
              //  intent = new Intent(getActivity(), SettingsActivity.class);
               // startActivity(intent);
                //break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
