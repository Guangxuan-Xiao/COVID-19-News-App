package com.example.covid_news.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "database.db";// 数据库名称
    public static final int VERSION = 1;

    public static final String TABLE_CHANNEL = "channel";
    public static final String TABLE_NEWS = "news";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ORDERID = "orderId";
    public static final String SELECTED = "selected";
    private Context context;

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作
        String sql = "create table if not exists channel " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id INTEGER , name TEXT , orderId INTEGER , selected SELECTED)";
        db.execSQL(sql);
        String sql2 = "create table if not exists news " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "visited integer, title varchar(100), time varchar(40), " +
                "source varchar(40), content varchar(4000), url varchar(200))";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
        onCreate(db);
    }

}
