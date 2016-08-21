package com.leavessilent.driverhomenews.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.activities.DetailActivity;
import com.leavessilent.driverhomenews.activities.MainActivity;
import com.leavessilent.driverhomenews.adapters.EvaluateNewsAdapter;
import com.leavessilent.driverhomenews.adapters.PictureNewsAdapter;
import com.leavessilent.driverhomenews.common.Constants;
import com.leavessilent.driverhomenews.common.Urls;
import com.leavessilent.driverhomenews.db.NewsDatabaseDB;
import com.leavessilent.driverhomenews.entity.News;
import com.leavessilent.driverhomenews.utils.JSONUtils;
import com.leavessilent.mylibrary.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {


    private View mView;
    private PullToRefreshListView mRefreshListView;
    private ListView mEvaluateListView;
    private List<News> mData;
    private PictureNewsAdapter mAdapter;
    private NewsDatabaseDB mDB;
    private MainActivity mMainActivity;

    public PictureFragment() {
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
        mView = inflater.inflate(R.layout.fragment_picture, container, false);
        initView();
        return mView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mMainActivity.setTitleText("图片");
            mMainActivity.unShowIcon();
        }
    }

    private void initView() {
        mRefreshListView = ((PullToRefreshListView) mView.findViewById(R.id.refresh_lv_picture));
        mEvaluateListView = mRefreshListView.getRefreshableView();

        mData = new ArrayList<>();
        mAdapter = new PictureNewsAdapter(getContext(), mData, R.layout.list_picture_item);
        mEvaluateListView.setAdapter(mAdapter);

        mRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mRefreshListView.setOnRefreshListener(this);

        mDB = NewsDatabaseDB.getInstance(getContext());
        List<News> newsList = mDB.getNewsList(Constants.TYPE_PICTURE);
        if (newsList.size() > 0) {
            mAdapter.updateData(newsList);
        } else {
            getDataFromInternet(State.UP);
        }

        mEvaluateListView.setOnItemClickListener(this);

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getDataFromInternet(State.DOWN);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getDataFromInternet(State.UP);
    }

    // -----------------------ListView的Item点击回调--------------------------------------------------------------
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", mData.get(position - 1).getId());
        intent.putExtra("type", Constants.TYPE_PICTURE);
        startActivity(intent);
    }

    private enum State {
        DOWN, UP
    }

    private void getDataFromInternet(final State state) {
        HttpUtils.getStringAsync(Urls.URL_PICTURE, new HttpUtils.RequestCallback() {
            @Override
            public void onFailure() {
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    List<News> list = JSONUtils.getNewsFromJson(result, Constants.TYPE_PICTURE);
                    switch (state) {
                        case UP:
                            mDB.saveNewsList(list);
                            mAdapter.addData(list);
                            break;
                        case DOWN:
                            mDB.deleteNewsList(Constants.TYPE_PICTURE);
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

}
