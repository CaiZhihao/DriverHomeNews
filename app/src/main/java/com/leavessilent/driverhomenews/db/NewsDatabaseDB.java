package com.leavessilent.driverhomenews.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leavessilent.driverhomenews.entity.Comment;
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
            String bigimgsrc = cursor.getString(cursor.getColumnIndex("bigimgsrc"));
            String smallimgsrc = cursor.getString(cursor.getColumnIndex("smallimgsrc"));
            newsList.add(new News(id, content, title, editor, desc, icon, bigimgsrc, smallimgsrc, postdata, newsType));
        }
        return newsList;
    }

    public void saveNews(News news) {
        mDb.execSQL("insert into news(id,content,postdata,editor,desc,title,icon,bigimgsrc,smallimgsrc,type) values(?,?,?,?,?,?,?,?,?,?)", new Object[]{news.getId(), news.getContent(), news.getPostdate(),
                news.getEditor(), news.getDesc(), news.getTitle(), news.getIcon(), news.getBigimgsrc(), news.getSmallimgsrc(), news.getType()});
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

    public News getNewsById(int id) {
        News news = null;
        Cursor cursor = mDb.rawQuery("select * from news where id = ? ", new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String postdata = cursor.getString(cursor.getColumnIndex("postdata"));
            String editor = cursor.getString(cursor.getColumnIndex("editor"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String icon = cursor.getString(cursor.getColumnIndex("icon"));
            String bigimgsrc = cursor.getString(cursor.getColumnIndex("bigimgsrc"));
            String smallimgsrc = cursor.getString(cursor.getColumnIndex("smallimgsrc"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            news = new News(id, content, title, editor, desc, icon, bigimgsrc, smallimgsrc, postdata, type);
        }
        return news;
    }

    /**
     * 有则修改，无则存储
     *
     * @param news
     */
    public void updateNews(News news) {
        if (getNewsById(news.getId()) == null) {
            saveNews(news);
        } else {
            mDb.execSQL("update news set content = ? where id = ?", new Object[]{news.getContent(), news.getId()});
        }
    }

    public List<Comment> getCommentList(int commentType) {
        List<Comment> newsList = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select * from comment where type = ? ", new String[]{String.valueOf(commentType)});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String simtitle = cursor.getString(cursor.getColumnIndex("simtitle"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            int support = cursor.getInt(cursor.getColumnIndex("support"));
            int oppose = cursor.getInt(cursor.getColumnIndex("oppose"));
            String postdate = cursor.getString(cursor.getColumnIndex("postdate"));
            newsList.add(new Comment(simtitle, content, id, username, support, oppose, postdate, commentType));
        }
        return newsList;
    }

    public void saveComment(Comment comment) {
        mDb.execSQL("insert into comment(id,simtitle,content,username,support,oppose,postdate,type) values(?,?,?,?,?,?,?,?)", new Object[]{comment.getId(), comment.getSimtitle(), comment.getContent(),
                comment.getUsername(), comment.getSupport(), comment.getOppose(), comment.getPostdate(), comment.getType()});
    }

    public void deleteCommentList(int type) {
        mDb.execSQL("delete from comment where type = ?", new Object[]{type});
    }

    public void saveCommentList(List<Comment> commentList) {
        if (commentList != null) {
            for (Comment comment : commentList) {
                saveComment(comment);
            }
        }
    }

}
