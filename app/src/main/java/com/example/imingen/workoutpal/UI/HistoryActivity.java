package com.example.imingen.workoutpal.UI;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.adapter.HistoryPagerAdapter;
import com.example.imingen.workoutpal.fragments.AchievementsTabFragment;
import com.example.imingen.workoutpal.fragments.HistoryTabFragment;
import com.example.imingen.workoutpal.fragments.NavigationDrawerFragment;

public class HistoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setUpTabIcons();

        setUpDrawer();
    }


    private void setupViewPager(ViewPager viewPager){
        HistoryPagerAdapter adapter = new HistoryPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AchievementsTabFragment(), "Achievements");
        adapter.addFragment(new HistoryTabFragment(), "History");
        viewPager.setAdapter(adapter);
    }

    private void setUpTabIcons() {
        tabLayout.getTabAt(0).setText(R.string.achievements_tab_text);
        tabLayout.getTabAt(1).setText(R.string.history_tab_text);

    }

    private void setUpDrawer(){
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_history);
    }

    @Override
    protected void onStart(){
        navigationDrawerFragment.updateCheckedItem(R.id.nav_history);
        super.onStart();
    }

}
