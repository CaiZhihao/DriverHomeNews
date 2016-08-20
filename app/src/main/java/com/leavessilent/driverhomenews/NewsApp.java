package com.leavessilent.driverhomenews;

import android.app.Application;

import com.leavessilent.mylibrary.ImageLoader;

/**
 * Created by Leavessilent on 2016/8/20.
 */
public class NewsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.setContext(this);
    }
}
