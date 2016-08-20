package com.leavessilent.driverhomenews.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leavessilent.driverhomenews.entity.News;

import java.util.List;

/**
 * Created by Administrator on 2016/8/6.
 */

public class JSONUtils {

    private static final String TAG = JSONUtils.class.getSimpleName();

    public static List<News> getNewsFromJson(String json, int type) {
        Gson gson = new Gson();
        List<News> newsList = gson.fromJson(json, new TypeToken<List<News>>() {
        }.getType());
        for (News news : newsList) {
            news.setType(type);
            Log.e(TAG, "getNewsFromJson: " + news.getTitle());
            Log.e(TAG, "getNewsFromJson: " + news.getIcon());
        }
        return newsList;
    }
}
