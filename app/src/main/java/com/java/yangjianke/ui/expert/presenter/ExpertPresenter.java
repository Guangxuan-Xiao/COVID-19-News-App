package com.java.yangjianke.ui.expert.presenter;

import android.content.Context;
import com.java.yangjianke.data.Expert;
import com.java.yangjianke.ui.expert.contract.ExpertContract;
import com.java.yangjianke.ui.expert.model.ExpertModel;
import com.java.yangjianke.util.NetworkUtil;

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

