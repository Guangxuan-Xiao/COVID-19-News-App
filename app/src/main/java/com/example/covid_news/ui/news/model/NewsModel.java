package com.example.covid_news.ui.news.model;

import com.example.covid_news.gson.NewsGson;
import com.example.covid_news.ui.news.contract.NewsContract;

public class NewsModel implements NewsContract.Model{
    @Override
    public void loadData( int type, final NewsContract.OnLoadFirstDataListener listener, int page) {
        //TO-DO: load data
        listener.onSuccess(null);
    }
}

