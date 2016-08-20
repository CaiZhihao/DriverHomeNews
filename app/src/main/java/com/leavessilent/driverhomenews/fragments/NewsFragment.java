package com.leavessilent.driverhomenews.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    // 初始化控件
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mLeftMenuView;
    private String[] data = {"List Item 01", "List Item 02", "List Item 03", "List Item 04", "List Item 05"};
    private ArrayAdapter mAdapter;
    private TextView mTitleText;
    private ViewStub mViewStub;
    private View mView;


    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            MainActivity main = (MainActivity) context;
            mToolbar = main.getToolbar();
            main.showIcon();
            main.setTitleText("  驱家新闻");
            main.getSupportActionBar().setHomeButtonEnabled(true);
            main.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        return mView;
    }

    private void initView() {

        mDrawerLayout = (DrawerLayout) mView.findViewById(R.id.dl_left);
        mLeftMenuView = (ListView) mView.findViewById(R.id.lv_left_menu);

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, data);
        mLeftMenuView.setAdapter(mAdapter);
    }

}
