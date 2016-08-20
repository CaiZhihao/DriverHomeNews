package com.leavessilent.mylibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Leavessilent on 2016/8/16.
 */
public class SDCardHelper {

    /**
     * 获取缓冲的路径
     *
     * @param context
     * @return
     */
    public static File getDiskCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        File file = new File(cachePath + File.separator + "Bitmap");
        if (!file.exists())
            file.mkdirs();
        return file;
    }

    /**
     * 将图片的URL进行MD5编码，编码的字符串唯一
     *
     * @param urlString
     * @return
     */
    public static String hashKeyForDisk(String urlString) {
        String cacheKey;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(urlString.getBytes());
            cacheKey = bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(urlString.hashCode());
        }
        return cacheKey;
    }


    /**
     * 将图片地址转换为文件
     *
     * @param context
     * @param urlString
     * @return
     */
    public static File urlToPath(Context context, String urlString) {
        File file = new File(getDiskCacheDir(context), hashKeyForDisk(urlString));
        return file;
    }

    /**
     * MD5的核心算法
     *
     * @param bytes
     * @return
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
