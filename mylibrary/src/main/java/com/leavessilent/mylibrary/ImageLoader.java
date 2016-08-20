package com.leavessilent.mylibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.leavessilent.mylibrary.cache.DiskCache;
import com.leavessilent.mylibrary.cache.RamCache;
import com.leavessilent.mylibrary.utils.HttpUtils;
import com.leavessilent.mylibrary.utils.ImageUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义的一个图片缓存，加载方法
 * <p/>
 * <p/>
 * PS: 引入该库是，每次请在application中初始化话contaxt;
 * Created by Leavessilent on 2016/8/16.
 */
public class ImageLoader {

    private static Context sContext;

    private static final int LOAD_IMAGE = 100;

    private static BlockingQueue<Runnable> sBlockingQueue = new LinkedBlockingQueue<>(128);

    private static ThreadPoolExecutor sThreadPoolExecutor = new ThreadPoolExecutor(5, 11, 10, TimeUnit.SECONDS, sBlockingQueue);


    public static void setContext(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        return sContext;
    }


    private static RamCache sRamCache = RamCache.getInstance();

    private static DiskCache sDiskCache = DiskCache.getInstance();
    ;

    /**
     * 图片加载
     *
     * @param container 要加载图片的控件
     * @param urlString 图片的url地址
     * @param reqWidth  imageview的宽
     * @param reqHeight imageview的高
     */
    public static void display(final ImageView container, final String urlString, final int reqWidth, final int reqHeight) {
        container.setTag(urlString);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case LOAD_IMAGE:
                        if (container.getTag().equals(urlString)) {
                            container.setImageBitmap((Bitmap) msg.obj);
                            AlphaAnimation animation = new AlphaAnimation(0, 1);
                            animation.setDuration(2000);
                            container.startAnimation(animation);
                        }
                        break;
                }
            }
        };


        if (sRamCache.get(urlString) != null) {
            container.setImageBitmap(sRamCache.get(urlString));
        } else {
            container.setImageResource(R.drawable.ic_launcher);
            sThreadPoolExecutor.execute(new Runnable() {


                @Override
                public void run() {
                    if (sDiskCache.get(sContext, urlString) != null) {
                        Bitmap bitmap = sDiskCache.get(sContext, urlString);
                        sRamCache.put(urlString, bitmap);
                        sendMessage(handler, bitmap);
                        return;
                    }
                    byte[] request = HttpUtils.request(urlString);
                    if (request != null) {
                        Bitmap bitmap = ImageUtils.sampleBitmap(request, reqWidth, reqHeight);
                        sRamCache.put(urlString, bitmap);
                        sDiskCache.put(sContext, urlString, bitmap);
                        sendMessage(handler, bitmap);
                        return;
                    }
                }
            });
        }

    }


    private static void sendMessage(Handler handler, Bitmap bitmap) {
        Message msg = Message.obtain();
        msg.what = LOAD_IMAGE;
        msg.obj = bitmap;
        handler.sendMessage(msg);
    }


}
