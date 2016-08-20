package com.leavessilent.driverhomenews.common;

/**
 * Created by Leavessilent on 2016/8/20.
 */
public class Urls {

    // 最新新闻广告和新闻列表url
    public static String URL_NEWS_ITEM_NEWEST_AD = "http://m.mydrivers.com/app/newstoppic.aspx?id=" + getCurrentTime();
    public static String URL_NEWS_ITEM_NEWEST = "http://m.mydrivers.com/app/newslist.aspx?tid=0&minId=0&maxId=0&ver=2.2&temp=" + getCurrentTime();

    // 硬件
    public static String URL_NEWS_ITEM_HARDWARE_AD = "http://m.mydrivers.com/app/newstoppic.aspx?t=168";
    public static String URL_NEWS_ITEM_HARDWARE = "http://m.mydrivers.com/app/newslist.aspx?tid=1&minId=0&maxId=0&ver=2.2&temp=" + getCurrentTime();

    // 软件
    public static String URL_NEWS_ITEM_SOFTWARE_AD = "http://m.mydrivers.com/app/newstoppic.aspx?t=169";
    public static String URL_NEWS_ITEM_SOFTWARE = "http://m.mydrivers.com/app/newslist.aspx?tid=2&minId=0&maxId=0&ver=2.2&temp=" + getCurrentTime();

    // 手机
    public static String URL_NEWS_ITEM_MOBILE_AD = "http://m.mydrivers.com/app/newstoppic.aspx?t=170";
    public static String URL_NEWS_ITEM_MOBILE = "http://m.mydrivers.com/app/newslist.aspx?tid=3&minId=0&maxId=0&ver=2.2&temp=" + getCurrentTime();

    // 科技
    public static String URL_NEWS_ITEM_SCIENCE_AD = "http://m.mydrivers.com/app/newstoppic.aspx?t=171";
    public static String URL_NEWS_ITEM_SCIENCE = "http://m.mydrivers.com/app/newslist.aspx?tid=4&minId=0&maxId=0&ver=2.2&temp=" + getCurrentTime();

    // 测评
    public static String URL_EVALUATE = "http://m.mydrivers.com/app/Newslist.aspx?hd=1&minId=0&maxId=0&ver=2.2&temp=" + getCurrentTime();

    // 图片
    public static String URL_PICTURE = "http://m.mydrivers.com/app/newspics.aspx?minId=0&maxId=0&temp=" + getCurrentTime();

    // 日热门评论
    public static String URL_COMMENT_DAY = "http://m.mydrivers.com/app/HotComments.aspx?day=1";

    // 周热门评论
    public static String URL_COMMENT_WEEK = "http://m.mydrivers.com/app/HotComments.aspx?day=7";

    public static String getNewsDetailUrl(int id) {
        return "http://m1.mydrivers.com/newscontent/detail_" + id + "_2.json";
    }

    private static long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
