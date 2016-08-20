package com.leavessilent.mylibrary.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.leavessilent.mylibrary.utils.SDCardHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Leavessilent on 2016/8/16.
 */
public class DiskCache {
    private static final String TAG = DiskCache.class.getSimpleName();
    private static DiskCache sImageCache = null;

    private DiskCache() {

    }

    public static DiskCache getInstance() {
        if (sImageCache == null) {
            synchronized (DiskCache.class) {
                if (sImageCache == null) {
                    sImageCache = new DiskCache();
                }
            }
        }
        return sImageCache;
    }

    /**
     * 向sd卡中缓存图片
     *
     * @param context
     * @param urlString
     * @param bitmap
     */
    public void put(Context context, String urlString, Bitmap bitmap) {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(SDCardHelper.urlToPath(context, urlString));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从url中获取图片
     *
     * @param context
     * @param urlString
     * @return
     */
    public Bitmap get(Context context, String urlString) {
        Bitmap bitmap = BitmapFactory.decodeFile(SDCardHelper.urlToPath(context, urlString).getAbsolutePath());
        return bitmap;
    }

}
