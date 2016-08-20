package com.leavessilent.driverhomenews.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import com.leavessilent.driverhomenews.adapters.NewsViewAdapter;
import com.leavessilent.driverhomenews.common.Constants;
import com.leavessilent.driverhomenews.common.Urls;

import java.util.ArrayList;
import java.util.List;

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
    private TextView mAdTitleText;
    private ViewStub mViewStub;
    private View mView;


    // TabLayout标题
    private String[] titles = {"最新", "硬件", "软件", "手机", "科技"};
    // 新闻列表的url
    private String[] urls = {
            Urls.URL_NEWS_ITEM_NEWEST,
            Urls.URL_NEWS_ITEM_HARDWARE,
            Urls.URL_NEWS_ITEM_SOFTWARE,
            Urls.URL_NEWS_ITEM_MOBILE,
            Urls.URL_NEWS_ITEM_SCIENCE};
    // 新闻列表type
    private int[] type = {
            Constants.TYPE_NEWS_ITEM_NEWEST,
            Constants.TYPE_NEWS_ITEM_HARDWARE,
            Constants.TYPE_NEWS_ITEM_SOFTWARE,
            Constants.TYPE_NEWS_ITEM_MOBILE,
            Constants.TYPE_NEWS_ITEM_SCIENCE};


    // 广告新闻的列表
    private String[] adUrls = {
            Urls.URL_NEWS_ITEM_NEWEST_AD,
            Urls.URL_NEWS_ITEM_HARDWARE_AD,
            Urls.URL_NEWS_ITEM_SOFTWARE_AD,
            Urls.URL_NEWS_ITEM_MOBILE_AD,
            Urls.URL_NEWS_ITEM_SCIENCE_AD};
    private int adTypes[] = {
            Constants.TYPE_NEWS_ITEM_NEWEST_AD,
            Constants.TYPE_NEWS_ITEM_HARDWARE_AD,
            Constants.TYPE_NEWS_ITEM_SOFTWARE_AD,
            Constants.TYPE_NEWS_ITEM_MOBILE_AD,
            Constants.TYPE_NEWS_ITEM_SCIENCE_AD
    };

    private ViewPager mNewsViewPager;

    // 新闻viewpager的数据源s
    private List<Fragment> mData;
    private NewsViewAdapter mNewsViewAdapter;
    private TabLayout mNewsTab;


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

        mNewsTab = (TabLayout) mView.findViewById(R.id.tablayout_news);


        mNewsViewPager = (ViewPager) mView.findViewById(R.id.viewpager_news);

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, data);
        mLeftMenuView.setAdapter(mAdapter);

        mData = new ArrayList<>();

        for (int i = 0; i < titles.length; i++) {
            NewsItemFragment fragment = new NewsItemFragment();
            fragment.setAdUrlStringAndType(adUrls[i], adTypes[i]);
            fragment.setUrlString(urls[i]);
            fragment.setType(type[i]);
            mData.add(fragment);
        }

        mNewsViewAdapter = new NewsViewAdapter(getActivity().getSupportFragmentManager(), mData, titles);
        mNewsViewPager.setAdapter(mNewsViewAdapter);

        mNewsTab.setupWithViewPager(mNewsViewPager);
    }

}
