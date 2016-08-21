package com.leavessilent.driverhomenews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.adapters.CommentAdapter;
import com.leavessilent.driverhomenews.db.NewsDatabaseDB;
import com.leavessilent.driverhomenews.entity.Comment;
import com.leavessilent.driverhomenews.utils.JSONUtils;
import com.leavessilent.mylibrary.utils.HttpUtils;
import com.leavessilent.mylibrary.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/7.
 */

public class CommentItemFragment extends Fragment {

    private View mView;
    private String mUrl;
    private int mType;

    private List<Comment> mData;
    private ListView mListView;
    private CommentAdapter mAdapter;

    private NewsDatabaseDB mDatabaseDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_comment_item, container, false);
        initView();
        return mView;
    }


    public void setUrlAndType(String url, int type) {
        mUrl = url;
        mType = type;
    }

    /**
     * 进入评论页面时，判断网络状态，如果连接则从网上获取最新的数据，否则从数据库获取数据
     */
    private void initView() {
        mDatabaseDB = NewsDatabaseDB.getInstance(getContext());
        mListView = ((ListView) mView.findViewById(R.id.list_fragment_comment_item));
        mData = new ArrayList<>();
        mAdapter = new CommentAdapter(getContext(), mData, R.layout.list_comment);
        mListView.setAdapter(mAdapter);

        if (NetworkUtils.checkNetWork(getContext())) {
            mDatabaseDB.deleteCommentList(mType);
            getDataFromInternet();
        } else {
            initData();
        }

    }

    private void initData() {
        List<Comment> commentList = mDatabaseDB.getCommentList(mType);
        if (commentList.size() > 0) {
            mAdapter.updateData(commentList);
        } else {
            getDataFromInternet();
        }
    }

    private void getDataFromInternet() {
        HttpUtils.getStringAsync(mUrl, new HttpUtils.RequestCallback() {
            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    List<Comment> list = JSONUtils.getCommentList(result, mType);
                    mDatabaseDB.saveCommentList(list);
                    mAdapter.updateData(list);
                }
            }

            @Override
            public void onFinish() {

            }
        });
    }


}
