package com.example.imingen.workoutpal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.imingen.workoutpal.fragments.AchievementsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marius on 16.10.2017.
 */

public class HistoryPagerAdapter extends FragmentPagerAdapter {


    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public HistoryPagerAdapter(FragmentManager manager){
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}

