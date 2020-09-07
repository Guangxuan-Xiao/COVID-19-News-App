package com.example.covid_news.ui.news.model;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.covid_news.data.News;
import com.example.covid_news.data.Paper;
import com.example.covid_news.ui.news.contract.NewsContract;
import com.example.covid_news.data.DataBase;
import com.example.covid_news.ui.news.NewsDao;
import com.example.covid_news.util.NetworkUtil;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.net.URL;

public class NewsModel implements NewsContract.Model {
    private static final DataBase db = new DataBase();
    private Context context;
    private NewsDao dao;
    List<News> newsList;
    Handler handler;

    public NewsModel(Context context){
        this.context = context;
        this.dao = new NewsDao(context);
    }

    @Override
    public void loadData(int type, final NewsContract.OnLoadFirstDataListener listener, int page) {
        boolean connected = NetworkUtil.checkNetworkState(context);
        if (connected){
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
                    if (type == 0){
                        newsList = db.getNewsList(page, 20);
                        for (News piece: newsList){
                            boolean exist = dao.searchNews(piece.getUrl());
                            if (!exist){
                                dao.addCache(piece, 0);
                            }
                        }
                        handler.sendEmptyMessage(123);
                    }
                    else if (type == 1){
                        List<Paper> paperList = db.getPaperList(page, 20);
                        newsList = new ArrayList<News>();
                        for (Paper p: paperList){
                            News piece = p.toNews();
                            boolean exist = dao.searchNews(piece.getUrl());
                            if (!exist){
                                dao.addCache(piece, 1);
                         }
                            newsList.add(piece);
                        }
                        handler.sendEmptyMessage(123);
                    }
                }
            }).start();
        }
        else{
            newsList = new ArrayList<News>();
            Toast toast = Toast.makeText(context, "网络连接不可用，加载本地缓存", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            String type_str = String.valueOf(type);
            List<Map<String, String>> newsListFromCache =
                    dao.listCache("type=?", new String[]{type_str}, page);
            for (Map<String, String> newsMap: newsListFromCache){
                News piece = new News();
                piece.content = newsMap.get("content");
                piece.title = newsMap.get("title");
                piece.source = newsMap.get("source");
                piece.time = newsMap.get("time");
                try {
                    piece.urls = new ArrayList<URL>();
                    piece.urls.add(new URL(newsMap.get("url")));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                newsList.add(piece);
            }
            listener.onSuccess(newsList);
        }

    }
}

