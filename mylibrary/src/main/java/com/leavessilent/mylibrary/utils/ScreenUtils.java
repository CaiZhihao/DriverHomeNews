package com.leavessilent.mylibrary.utils;

import com.leavessilent.mylibrary.ImageLoader;

/**
 * Created by Leavessilent on 2016/8/19.
 */
public class ScreenUtils {
    public static int getScreenWidth() {
        return ImageLoader.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return ImageLoader.getContext().getResources().getDisplayMetrics().heightPixels;
    }
}
