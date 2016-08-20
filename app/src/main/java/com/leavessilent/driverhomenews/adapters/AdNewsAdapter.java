package com.leavessilent.driverhomenews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leavessilent on 2016/8/20.
 */
public class AdNewsAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mData;

    public AdNewsAdapter(FragmentManager fm, List<Fragment> data) {
        super(fm);
        if (data == null) {
            mData = new ArrayList<>();
        } else {
            mData = data;
        }
    }

    public void update(List<Fragment> data) {
        if (data != null) {
            mData = data;
            notifyDataSetChanged();
        }
    }


    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
