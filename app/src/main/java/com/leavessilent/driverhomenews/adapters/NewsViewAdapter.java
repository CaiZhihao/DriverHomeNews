package com.leavessilent.driverhomenews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Leavessilent on 2016/8/20.
 */
public class NewsViewAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mData;
    private String[] titles;

    public NewsViewAdapter(FragmentManager fm, List<Fragment> data, String[] titles) {
        super(fm);
        mData = data;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
