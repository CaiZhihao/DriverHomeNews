package com.leavessilent.driverhomenews.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.leavessilent.driverhomenews.R;

public class SplashActivity extends AppCompatActivity {

    private static final int TO_MAIN = 20;
    private ImageView mSplashImage;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_MAIN:
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        // 隐藏actionbar
        getSupportActionBar().hide();

        // 为闪屏页设置动画
        mSplashImage = (ImageView) findViewById(R.id.image_splash);
        AlphaAnimation animation = new AlphaAnimation(0, 1f);
        animation.setDuration(2000);
        mSplashImage.startAnimation(animation);

        mHandler.sendEmptyMessageDelayed(TO_MAIN, 2000);
    }
}
