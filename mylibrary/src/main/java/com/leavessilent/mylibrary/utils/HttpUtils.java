package com.leavessilent.mylibrary.utils;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Leavessilent on 2016/8/12.
 */
public class HttpUtils {

    private static final int LOAD_SUCCESS = 105;
    private static final int LOAD_FAILED = 106;
    /**
     * 根据url获取数据
     * 返回为string类型
     *
     * @param url
     * @return
     */


    private static BlockingQueue<Runnable> sBlockingQueue = new LinkedBlockingQueue<>(128);

    private static ThreadPoolExecutor sStringThreadPoolExecutor = new ThreadPoolExecutor(5, 11, 10, TimeUnit.SECONDS, sBlockingQueue);

    public static String requestString(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据url获取数据
     * 返回值为byte数组
     *
     * @param url
     * @return
     */
    public static byte[] request(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().bytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void getStringAsync(final String urlString, final RequestCallback callback) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case LOAD_SUCCESS:
                        callback.onSuccess(msg.obj.toString());
                        break;
                    case LOAD_FAILED:
                        callback.onFailure();
                        break;
                }
                callback.onFinish();
            }
        };


        sStringThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String requestString = requestString(urlString);
                if (requestString != null) {
                    Message msg = Message.obtain();
                    msg.what = LOAD_SUCCESS;
                    msg.obj = requestString;
                    handler.sendMessage(msg);
                } else {
                    handler.sendEmptyMessage(LOAD_FAILED);
                }
            }
        });
    }


    public interface RequestCallback {

        void onFailure();

        void onSuccess(String result);

        void onFinish();
    }
}
