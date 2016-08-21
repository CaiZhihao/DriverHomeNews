package com.leavessilent.driverhomenews.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leavessilent.driverhomenews.entity.Comment;
import com.leavessilent.driverhomenews.entity.News;

import org.json.JSONException;
import org.json.JSONObject;

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
        }
        return newsList;
    }


    public static List<Comment> getCommentList(String json, int type) {
        try {
            JSONObject object = new JSONObject(json);
            String all = object.getJSONArray("All").toString();
            Gson gson = new Gson();
            List<Comment> commentList = gson.fromJson(all, new TypeToken<List<Comment>>() {
            }.getType());
            for (Comment comment : commentList) {
                comment.setType(type);
                Log.e(TAG, "getCommentList: " + comment.getSimtitle());
            }
            return commentList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
