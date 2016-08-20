package com.leavessilent.driverhomenews.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.fragments.CommentFragment;
import com.leavessilent.driverhomenews.fragments.EvaluateFragment;
import com.leavessilent.driverhomenews.fragments.NewsFragment;
import com.leavessilent.driverhomenews.fragments.PictureFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    private Toolbar mToolbar;
    private TextView mTitleText;
    private ActionBar mSupportActionBar;
    private FragmentTransaction mTransaction;
    private NewsFragment mNewsFragment;
    private ViewStub mViewStub;
    private EvaluateFragment mEvaluateFragment;
    private PictureFragment mPictureFragment;
    private CommentFragment mCommentFragment;
    private List<Fragment> mFragmentList;
    private RadioButton mNewsBtn;
    private RadioButton mEvaluateBtn;
    private RadioButton mPictureBtn;
    private RadioButton mCommentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    /**
     * 初始化控件
     */
    private void initView() {
        mSupportActionBar = getSupportActionBar();
        mSupportActionBar.hide();
        mToolbar = (Toolbar) findViewById(R.id.tl_custom);
        mViewStub = (ViewStub) findViewById(R.id.viewstub_icon);
        mTitleText = (TextView) findViewById(R.id.tl_title);

        mNewsBtn = (RadioButton) findViewById(R.id.radio_btn_news);
        mEvaluateBtn = (RadioButton) findViewById(R.id.radio_btn_evaluate);
        mPictureBtn = (RadioButton) findViewById(R.id.radio_btn_picture);
        mCommentBtn = (RadioButton) findViewById(R.id.radio_btn_comment);

        mFragmentList = new ArrayList<>();
        // 为每个radiobtn设置监听
        mNewsBtn.setOnCheckedChangeListener(this);
        mEvaluateBtn.setOnCheckedChangeListener(this);
        mPictureBtn.setOnCheckedChangeListener(this);
        mCommentBtn.setOnCheckedChangeListener(this);


        mToolbar.setTitleTextColor(Color.WHITE);

        mNewsFragment = new NewsFragment();
        mEvaluateFragment = new EvaluateFragment();
        mPictureFragment = new PictureFragment();
        mCommentFragment = new CommentFragment();

        mFragmentList.add(mNewsFragment);
        mFragmentList.add(mEvaluateFragment);
        mFragmentList.add(mPictureFragment);
        mFragmentList.add(mCommentFragment);

        // 将所有fragment添加到容器
        addFragmentList(mFragmentList);

        //将所有的fragment隐藏
        hideFragmentList(mFragmentList);

        showFragment(0);

    }

    /**
     * 开启事务
     */
    private void beginTransaction() {
        mTransaction = getSupportFragmentManager().beginTransaction();
    }

    private void addFragmentList(List<Fragment> fragmentList) {
        for (Fragment fragment : fragmentList) {
            beginTransaction();
            mTransaction.add(R.id.container, fragment).commit();
        }
    }

    private void hideFragmentList(List<Fragment> fragmentList) {
        for (Fragment fragment : fragmentList) {
            beginTransaction();
            mTransaction.hide(fragment).commit();
        }
    }

    /**
     * 显示制定位置的fragment
     *
     * @param position
     */
    private void showFragment(int position) {
        hideFragmentList(mFragmentList);
        beginTransaction();
        mTransaction.show(mFragmentList.get(position)).commit();
    }

    /**
     * 为每个fragment提供设置标题方法
     *
     * @param titleText
     */
    public void setTitleText(String titleText) {
        mTitleText.setText(titleText);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void showIcon() {
        mViewStub.inflate();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.radio_btn_news:
                    showFragment(0);
                    break;
                case R.id.radio_btn_evaluate:
                    showFragment(1);
                    break;
                case R.id.radio_btn_picture:
                    showFragment(2);
                    break;
                case R.id.radio_btn_comment:
                    showFragment(3);
                    break;
            }
        }
    }
}
