package com.leavessilent.mylibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.leavessilent.mylibrary.ImageLoader;

/**
 * Created by Leavessilent on 2016/8/16.
 */
public class ImageUtils {

    /**
     * 压缩图片
     *
     * @param bytes     图片的byte数组
     * @param reqWidth  需要的宽
     * @param reqHeight 需要的高
     * @return
     */
    public static Bitmap sampleBitmap(byte[] bytes, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 四舍五入
            int heightRatio = Math.round((float) height / (float) dip2px(reqWidth));
            int widthRatio = Math.round((float) width / (float) dip2px(reqHeight));
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 将图片压缩到原图片的一半。
     *
     * @param bytes
     * @return
     */
    public static Bitmap sampleHalfBitmap(byte[] bytes) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        int scale = 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * dp 转px单位
     *
     * @param dpValue
     * @return
     */
    private static int dip2px(int dpValue) {
        float scale = ImageLoader.getContext().getResources().getDisplayMetrics().density;
        return ((int) (dpValue * scale + 0.5f));
    }

    /**
     * 根据手机的分辨率，从px单位转成dp
     *
     * @param pxValue
     * @return
     */
    private static int px2dip(int pxValue) {
        float scale = ImageLoader.getContext().getResources().getDisplayMetrics().density;
        return ((int) (pxValue / scale + 0.5f));
    }
}
