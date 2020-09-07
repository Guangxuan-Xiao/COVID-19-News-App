package com.example.covid_news.ui.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;
import com.example.covid_news.db.SQLHelper;
import com.example.covid_news.data.News;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsDao {

    private SQLHelper helper = null;

    public NewsDao(Context context) { helper = new SQLHelper(context); }

    public boolean addCache(News item) {
        boolean flag = false;
        SQLiteDatabase database = null;
        long id = -1;
        try {
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("visited", 0);
            values.put("title", item.getTitle());
            values.put("time", item.getTime());
            values.put("source", item.getSource());
            values.put("content", item.getContent());
            values.put("url", item.getUrl());
            id = database.insert("news", null, values);
            flag = (id != -1 ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    public boolean deleteCache(String whereClause, String[] whereArgs) {
        boolean flag = false;
        SQLiteDatabase database = null;
        int count = 0;
        try {
            database = helper.getWritableDatabase();
            count = database.delete("news", whereClause, whereArgs);
            flag = (count > 0 ? true : false);
        } catch (Exception e) {
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    public boolean updateCache(ContentValues values, String whereClause,
                               String[] whereArgs) {
        boolean flag = false;
        SQLiteDatabase database = null;
        int count = 0;
        try {
            database = helper.getWritableDatabase();
            count = database.update("news", values, whereClause, whereArgs);
            flag = (count > 0 ? true : false);
        } catch (Exception e) {
        } finally {
            if (database != null) {
                database.close();
            }
        }
        if (flag){
            Log.i("Update", count + " visited");
        }
        return flag;
    }

    public boolean searchNews(String str){
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try{
            database = helper.getReadableDatabase();
            cursor = database.rawQuery("select * from news where url=?", new String[]{str});
            while (cursor.moveToNext()){
                database.close();
                Log.i("News exists", str);
                return true;
            }
            database.close();
            Log.i("News doesn't exist", str);
            return false;
        } catch (Exception e) {
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return true;
    }

    public Map<String, String> viewCache(String selection, String[] selectionArgs) {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            database = helper.getReadableDatabase();
            cursor = database.query(true, "news", null, selection,
                    selectionArgs, null, null, null, null);
            int cols_len = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = cursor.getColumnName(i);
                    String cols_values = cursor.getString(cursor
                            .getColumnIndex(cols_name));
                    if (cols_values == null) {
                        cols_values = "";
                    }
                    map.put(cols_name, cols_values);
                }
            }
        } catch (Exception e) {
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return map;
    }

    public List<Map<String, String>> listCache(String selection, String[] selectionArgs, int page) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        int offset = page * 20 - 19;
        String limit = "20 OFFSET " + offset;
        try {
            database = helper.getReadableDatabase();
            cursor = database.query(false, "news", null, selection, selectionArgs, null, null, "time desc", limit);
            int cols_len = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = cursor.getColumnName(i);
                    String cols_values = cursor.getString(cursor.getColumnIndex(cols_name));
                    if (cols_values == null) {
                        cols_values = "";
                    }
                    map.put(cols_name, cols_values);
                }
                list.add(map);
            }

        } catch (Exception e) {
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return list;
    }

    public void clearFeedTable() {
        String sql = "DELETE FROM news;";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql);
        revertSeq();
    }
    private void revertSeq() {
        String sql = "update sqlite_sequence set seq=0 where name='news'";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql);
    }

//    public void sort(String keyCol, boolean desc){
//        SQLiteDatabase db = helper.getWritableDatabase();
//        String mode = desc ? "DESC" : "ASC";
//        db.execSQL("SELECT * FROM news ORDER BY " + keyCol + " " + mode);
//    }
}
