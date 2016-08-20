package com.leavessilent.driverhomenews.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.adapters.AdNewsAdapter;
import com.leavessilent.driverhomenews.adapters.NewsAdapter;
import com.leavessilent.driverhomenews.db.NewsDatabaseDB;
import com.leavessilent.driverhomenews.entity.News;
import com.leavessilent.driverhomenews.utils.JSONUtils;
import com.leavessilent.mylibrary.ImageLoader;
import com.leavessilent.mylibrary.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsItemFragment extends Fragment implements Handler.Callback {

    private NewsDatabaseDB mDatabaseDB;

    private static final int LOAD_VIEWPAGER = 22;
    private static final int LOAD_LISTVIEW = 23;
    private static final String TAG = NewsItemFragment.class.getSimpleName();
    // 我们要返回的view
    private View mView;
    // 实例化组件
    private PullToRefreshListView mRefreshListView;
    private ListView mListView;


    // 展示广告的viewpager
    private ViewPager mAdNewsViewPager;
    //使用viewstub来决定是否显示viewpager指示点
    private ViewStub mViewStub;

    // 三个指示点
    private List<RadioButton> mRadioButtonList;


    private RadioButton mFirstDot;
    private RadioButton mSecondDot;
    private RadioButton mThirdDot;
    // viewpager下面的文字
    private TextView mAdTitle;

    // viewpager数据源
    private List<News> mAdNewsList;

    // listView数据源
    private List<News> mNewsList;

    // viewpager 适配器
    private AdNewsAdapter mAdNewsAdapter;

    // viewpager 适配器数据源
    private List<Fragment> mAdData;

    // 异步处理
    private Handler mHandler = new Handler(this);

    private String mUrlString;

    // 判断是哪种类型的新闻
    private int type;


    private NewsAdapter mNewsAdapter;

    // ListView的header
    private View mHeader;

    public void setUrlString(String urlString) {
        mUrlString = urlString;
    }

    public NewsItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_news_item, container, false);
        initView();
        return mView;

    }

    private void initView() {
        mAdNewsList = new ArrayList<>();
        mNewsList = new ArrayList<>();
        mAdData = new ArrayList<>();
        mDatabaseDB = NewsDatabaseDB.getInstance(getContext());

        mRefreshListView = (PullToRefreshListView) mView.findViewById(R.id.pull_to_refresh_list_view);
        mRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView = mRefreshListView.getRefreshableView();

        mNewsAdapter = new NewsAdapter(getContext(), mNewsList, R.layout.news_item);
        mListView.setAdapter(mNewsAdapter);

        mHeader = LayoutInflater.from(getContext()).inflate(R.layout.list_header_layout, mListView, false);
        mListView.addHeaderView(mHeader);

        initData();
    }

    public void initData() {
        List<News> newsList = mDatabaseDB.getNewsList(type);
        if (newsList.size() > 0) {
            mNewsList.addAll(newsList);
            mNewsAdapter.notifyDataSetChanged();
        } else {
            Log.e(TAG, "initData: " + mUrlString);
            HttpUtils.getStringAsync(mUrlString, new HttpUtils.RequestCallback() {
                @Override
                public void onFailure() {
                    Toast.makeText(ImageLoader.getContext(), "网络错误，请稍后再试", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(String result) {
                    if (result != null) {
                        List<News> newsFromJson = JSONUtils.getNewsFromJson(result, type);
                        mNewsAdapter.updateData(newsFromJson);
                        mDatabaseDB.saveNewsList(newsFromJson);
                    }
                    mHandler.sendEmptyMessage(LOAD_LISTVIEW);
                }

                @Override
                public void onFinish() {

                }
            });
        }
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LOAD_VIEWPAGER:
                break;
            case LOAD_LISTVIEW:
                mNewsAdapter.notifyDataSetChanged();
                break;
        }
        return false;
    }


    public void setType(int type) {
        this.type = type;
    }
}
