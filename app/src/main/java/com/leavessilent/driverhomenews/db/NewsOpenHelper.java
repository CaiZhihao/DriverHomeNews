package com.leavessilent.driverhomenews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Leavessilent on 2016/8/20.
 */
public class NewsOpenHelper extends SQLiteOpenHelper {

    private final String CREATE_NEWS = "create table if not exists news(_id integer primary key autoincrement, " +
            "id integer," +
            "content text," +
            "postdata text," +
            "editor text," +
            "desc text," +
            "title text," +
            "icon text," +
            "bigimgsrc text," +
            "smallimgsrc text," +
            "type integer)";


    private final String CREATE_COMMENT = "create table if not exists comment(_id integer primary key autoincrement," +
            "id integer," +
            "simtitle text," +
            "content text," +
            "username text," +
            "support integer," +
            "oppose int," +
            "postdate text," +
            "type integer)";

    public NewsOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS);
        db.execSQL(CREATE_COMMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
