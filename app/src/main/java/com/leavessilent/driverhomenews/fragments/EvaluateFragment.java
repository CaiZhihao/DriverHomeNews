package com.leavessilent.driverhomenews.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.activities.DetailActivity;
import com.leavessilent.driverhomenews.activities.MainActivity;
import com.leavessilent.driverhomenews.adapters.EvaluateNewsAdapter;
import com.leavessilent.driverhomenews.common.Constants;
import com.leavessilent.driverhomenews.common.Urls;
import com.leavessilent.driverhomenews.db.NewsDatabaseDB;
import com.leavessilent.driverhomenews.entity.News;
import com.leavessilent.driverhomenews.utils.JSONUtils;
import com.leavessilent.mylibrary.utils.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluateFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {


    private View mView;
    private PullToRefreshListView mRefreshListView;
    private ListView mEvaluateListView;
    private List<News> mData;
    private EvaluateNewsAdapter mAdapter;
    private NewsDatabaseDB mDB;
    private MainActivity mMainActivity;

    public EvaluateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mMainActivity = ((MainActivity) context);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_evaluate, container, false);
        initView();
        return mView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mMainActivity.setTitleText("评测");
            mMainActivity.unShowIcon();
        }
    }

    private void initView() {
        mRefreshListView = ((PullToRefreshListView) mView.findViewById(R.id.refresh_lv_evaluate));
        mEvaluateListView = mRefreshListView.getRefreshableView();

        mData = new ArrayList<>();
        mAdapter = new EvaluateNewsAdapter(getContext(), mData, R.layout.list_evaluate_item);
        mEvaluateListView.setAdapter(mAdapter);

        mRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        ILoadingLayout startLoadingView = mRefreshListView.getLoadingLayoutProxy(true, false);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
        startLoadingView.setLastUpdatedLabel("最近更新" + sdf.format(date));

        mRefreshListView.setOnRefreshListener(this);

        mDB = NewsDatabaseDB.getInstance(getContext());
        List<News> newsList = mDB.getNewsList(Constants.TYPE_EVALUATE);
        if (newsList.size() > 0) {
            mAdapter.updateData(newsList);
        } else {
            getDataFromInternet(State.UP);
        }

        mEvaluateListView.setOnItemClickListener(this);

    }

    private void getDataFromInternet(final State state) {
        HttpUtils.getStringAsync(Urls.URL_EVALUATE, new HttpUtils.RequestCallback() {
            @Override
            public void onFailure() {
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    List<News> list = JSONUtils.getNewsFromJson(result, Constants.TYPE_EVALUATE);
                    switch (state) {
                        case UP:
                            mDB.saveNewsList(list);
                            mAdapter.addData(list);
                            break;
                        case DOWN:
                            mDB.deleteNewsList(Constants.TYPE_EVALUATE);
                            mDB.saveNewsList(list);
                            mAdapter.updateData(list);
                            break;
                    }
                }
            }

            @Override
            public void onFinish() {
                mRefreshListView.onRefreshComplete();
            }
        });
    }

    // ----------------------------因为我们为ListView设置了Headerview，所以这里的position应该减一------------------------------------
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", mData.get(position - 1).getId());
        intent.putExtra("type", Constants.TYPE_EVALUATE);
        startActivity(intent);
    }


    private enum State {
        DOWN, UP
    }

    // PullToRefreshListView的刷新回调
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getDataFromInternet(State.DOWN);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getDataFromInternet(State.UP);
    }
}
