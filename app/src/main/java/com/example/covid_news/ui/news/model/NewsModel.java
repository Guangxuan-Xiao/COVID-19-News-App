package com.example.covid_news.ui.news.model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import com.example.covid_news.data.News;
import com.example.covid_news.ui.news.contract.NewsContract;
import com.example.covid_news.data.DataBase;

import java.util.ArrayList;
import java.util.List;

public class NewsModel implements NewsContract.Model {
    private static final DataBase db = new DataBase();
    List<News> newsList;
    Handler handler;

    @Override
    public void loadData(int type, final NewsContract.OnLoadFirstDataListener listener, int page) {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 123) {
                    listener.onSuccess(newsList);
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsList = db.getNewsList(page, 20);
                handler.sendEmptyMessage(123);
            }
        }).start();

    }
}

