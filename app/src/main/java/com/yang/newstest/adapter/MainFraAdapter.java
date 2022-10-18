package com.yang.newstest.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MainFraAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragment;

    public MainFraAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragment = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
