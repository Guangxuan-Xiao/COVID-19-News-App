package com.java.yangjianke.ui.news.presenter;

import android.content.Context;

import com.java.yangjianke.gson.NewsGson;
import com.java.yangjianke.data.News;
import com.java.yangjianke.ui.news.contract.NewsContract;
import com.java.yangjianke.ui.news.model.NewsModel;
import com.java.yangjianke.util.NetworkUtil;

import java.util.List;

public class NewsPresenter implements NewsContract.Presenter, NewsContract.OnLoadFirstDataListener {
    private NewsContract.View view;
    private NewsContract.Model model;
    private Context context;

    public NewsPresenter(NewsContract.View view,Context context) {
        this.view = view;
        this.model = new NewsModel(context);
        this.context=context;
    }

    @Override
    public void loadData(int type, int page) {
        model.loadData(type,this,page);
    }

    @Override
    public void onSuccess(List<News> list) {
        view.returnData(list);
    }

    @Override
    public void onFailure(String str, Throwable e) {
        NetworkUtil.checkNetworkState(context);
    }

}

