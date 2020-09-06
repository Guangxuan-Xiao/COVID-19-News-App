package com.example.covid_news.ui.news.model;

import com.example.covid_news.data.News;
import com.example.covid_news.ui.news.contract.NewsContract;

import java.util.ArrayList;
import java.util.List;

public class NewsModel implements NewsContract.Model{
    @Override
    public void loadData(int type, final NewsContract.OnLoadFirstDataListener listener, int page) {
        //TO-DO: load data
        //type 0: News, type 1: Paper
        //size: default (20)

        List<News> newsList = new ArrayList<News>();
        listener.onSuccess(newsList);
    }
}

