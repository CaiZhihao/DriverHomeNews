package com.leavessilent.driverhomenews.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.adapters.CommentViewAdapter;
import com.leavessilent.driverhomenews.common.Constants;
import com.leavessilent.driverhomenews.common.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {


    private View mView;
    private ViewPager mCommentViewPager;
    private List<Fragment> mData;
    private TabLayout mTabLayout;

    private String titles[] = {"日热梦评论", "周热门评论", "我的评论"};
    private CommentViewAdapter mAdapter;

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_comment, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mCommentViewPager = ((ViewPager) mView.findViewById(R.id.viewpager_comment));
        mTabLayout = (TabLayout) mView.findViewById(R.id.tablayout_comment);
        mData = new ArrayList<>();

        initFragment();

        mAdapter = new CommentViewAdapter(getChildFragmentManager(), mData, titles);
        mCommentViewPager.setAdapter(mAdapter);
        // 为ViewPager设置最多同时可存在的视图，当设置为2时，同时可存在五个页面
        mCommentViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mCommentViewPager);
    }

    /**
     * 初始化CommentViewPager的数据源
     */
    private void initFragment() {
        CommentItemFragment item1 = new CommentItemFragment();
        item1.setUrlAndType(Urls.URL_COMMENT_DAY, Constants.TYPE_COMMENT_DAY);
        CommentItemFragment item2 = new CommentItemFragment();
        item2.setUrlAndType(Urls.URL_COMMENT_WEEK, Constants.TYPE_COMMENT_WEEK);
        BlankFragment item3 = new BlankFragment();
        mData.add(item1);
        mData.add(item2);
        mData.add(item3);
    }


}
