package com.example.covid_news.ui.expert.contract;

import com.example.covid_news.data.Expert;
import com.example.covid_news.data.News;

import java.util.List;

public interface ExpertContract {
    interface View {
        void returnData(List<Expert> data);
    }

    interface OnLoadFirstDataListener {
        void onSuccess(List<Expert> list);

        void onFailure(String str, Throwable e);
    }

    interface Presenter {
        void loadData();
    }

    interface Model {
        void loadData(OnLoadFirstDataListener listener);
    }
}
