package com.leavessilent.driverhomenews.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.common.Urls;
import com.leavessilent.driverhomenews.db.NewsDatabaseDB;
import com.leavessilent.driverhomenews.entity.News;
import com.leavessilent.driverhomenews.utils.JSONUtils;
import com.leavessilent.mylibrary.utils.HttpUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {


    public static final int LOAD_DATA = 100;

    private static final String TAG = DetailActivity.class.getSimpleName();
    private TextView mTextView;
    private WebView mContentView;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            News news = (News) msg.obj;
            mTextView.setText(news.getPostdate() + " " + news.getEditor());
            mContentView.loadDataWithBaseURL(null, news.getContent(), "text/html", "utf-8", null);
        }
    };
    private NewsDatabaseDB mDB;
    private String mNewsDetailUrl;
    private int mType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
    }

    private void initView() {
        getSupportActionBar().hide();

        mDB = NewsDatabaseDB.getInstance(this);
        mTextView = (TextView) findViewById(R.id.tv_title);
        mContentView = (WebView) findViewById(R.id.webview_content);
        mContentView.getSettings().setJavaScriptEnabled(true);
        mContentView.getSettings().setBlockNetworkImage(false);
        mContentView.getSettings().setDefaultFontSize(18);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        mType = intent.getIntExtra("type", 0);
        mNewsDetailUrl = Urls.getNewsDetailUrl(id);

        News news = mDB.getNewsById(id);
        if (news == null || TextUtils.isEmpty(news.getContent())) {
            getDataFromInternet();
        } else {
            mTextView.setText(news.getPostdate() + " " + news.getEditor());
            mContentView.loadDataWithBaseURL(null, news.getContent(), "text/html", "utf-8", null);
        }
    }

    private void getDataFromInternet() {
        HttpUtils.getStringAsync(mNewsDetailUrl, new HttpUtils.RequestCallback() {

            @Override
            public void onFailure() {
                Toast.makeText(DetailActivity.this, "当前网络较差，请刷新重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    News detaildNews = JSONUtils.getDetaildNews(result, mType);
                    mDB.updateNews(detaildNews);
                    Message msg = Message.obtain();
                    msg.what = LOAD_DATA;
                    msg.obj = detaildNews;
                    mHandler.sendMessage(msg);
                }
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
