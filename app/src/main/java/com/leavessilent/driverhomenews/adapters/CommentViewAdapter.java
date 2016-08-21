package com.leavessilent.driverhomenews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/8/7.
 */

public class CommentViewAdapter extends FragmentPagerAdapter {
    private List<Fragment> mData;
    private String[] mTitles;

    public CommentViewAdapter(FragmentManager fm, List<Fragment> data, String[] titles) {
        super(fm);
        mData = data;
        mTitles = titles;
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
        return mTitles[position];
    }
}
