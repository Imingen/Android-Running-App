package com.example.imingen.workoutpal.UI;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.fragments.NavigationDrawerFragment;
import com.example.imingen.workoutpal.fragments.SettingsFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpDrawer();

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(R.id.pref_content, new SettingsFragment())
                .commit();
    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_settings);
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationDrawerFragment.updateCheckedItem(R.id.nav_settings);
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_signout, menu);
        return true;
    }


    private void sendToLoginPage(){
        Intent loginIntent = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.signoutButton:
                FirebaseAuth.getInstance().signOut();
                sendToLoginPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
