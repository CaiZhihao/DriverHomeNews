package com.leavessilent.mylibrary.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Leavessilent on 2016/8/16.
 */
public class RamCache {
    private LruCache<String, Bitmap> mImageCache;

    private static RamCache sRamCache = null;

    private RamCache() {

        long maxMemory = Runtime.getRuntime().maxMemory();
        int max = ((int) (maxMemory / 8));
        mImageCache = new LruCache<String, Bitmap>(max) {

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public static RamCache getInstance() {

        if (sRamCache == null) {
            synchronized (RamCache.class) {
                if (sRamCache == null)
                    sRamCache = new RamCache();
            }
        }
        return sRamCache;
    }

    public void put(String urlString, Bitmap bitmap) {
        mImageCache.put(urlString, bitmap);
    }

    /**
     * 获取
     * @param urlString
     * @return
     */
    public Bitmap get(String urlString) {
        return mImageCache.get(urlString);
    }


}
