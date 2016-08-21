package com.leavessilent.driverhomenews.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leavessilent.driverhomenews.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leavessilent on 2016/8/20.
 */
public class NewsDatabaseDB {

    private SQLiteDatabase mDb;
    private NewsOpenHelper mHelper;
    private static NewsDatabaseDB sNewDatabaseDB;

    private NewsDatabaseDB(Context context) {
        mHelper = new NewsOpenHelper(context, "DriverHomeNews", null, 1);
        mDb = mHelper.getReadableDatabase();
    }

    public static NewsDatabaseDB getInstance(Context context) {
        if (sNewDatabaseDB == null) {
            synchronized (NewsDatabaseDB.class) {
                if (sNewDatabaseDB == null) {
                    sNewDatabaseDB = new NewsDatabaseDB(context);
                }
            }
        }
        return sNewDatabaseDB;
    }


    public List<News> getNewsList(int newsType) {
        List<News> newsList = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select * from news where type = ? ", new String[]{String.valueOf(newsType)});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String postdata = cursor.getString(cursor.getColumnIndex("postdata"));
            String editor = cursor.getString(cursor.getColumnIndex("editor"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String icon = cursor.getString(cursor.getColumnIndex("icon"));
            News news = new News(id, content, title, editor, desc, icon, postdata, newsType);
            newsList.add(news);
        }
        return newsList;
    }

    public void saveNews(News news) {
        mDb.execSQL("insert into news(id,content,postdata,editor,desc,title,icon,type) values(?,?,?,?,?,?,?,?)", new Object[]{news.getId(), news.getContentt(), news.getPostdate(),
                news.getEditor(), news.getDesc(), news.getTitle(), news.getIcon(), news.getType()});
    }

    public void saveNewsList(List<News> newsList) {
        if (newsList != null) {
            for (News news : newsList) {
                saveNews(news);
            }
        }
    }

    public void deleteNewsList(int type) {
        mDb.execSQL("delete from news where type = ?", new Object[]{type});
    }
}
