package com.example.covid_news.ui.expert.presenter;

import android.content.Context;
import com.example.covid_news.data.Expert;
import com.example.covid_news.ui.expert.contract.ExpertContract;
import com.example.covid_news.ui.expert.model.ExpertModel;
import com.example.covid_news.util.NetworkUtil;

import java.util.List;

public class ExpertPresenter implements ExpertContract.Presenter, ExpertContract.OnLoadFirstDataListener {
    private ExpertContract.View view;
    private ExpertContract.Model model;
    private Context context;

    public ExpertPresenter(ExpertContract.View view, Context context) {
        this.view = view;
        this.model = new ExpertModel(context);
        this.context = context;
    }

    @Override
    public void loadData() {
        model.loadData(this);
    }

    @Override
    public void onSuccess(List<Expert> list) {
        view.returnData(list);
    }

    @Override
    public void onFailure(String str, Throwable e) {
        NetworkUtil.checkNetworkState(context);
    }

}

