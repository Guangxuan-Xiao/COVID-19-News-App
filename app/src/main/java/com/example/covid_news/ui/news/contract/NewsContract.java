package com.example.covid_news.ui.news.contract;

import com.example.covid_news.data.News;
import java.util.List;

public interface NewsContract {


    interface View{
        void returnData(List<News> data);
    }

    interface OnLoadFirstDataListener{
        void  onSuccess(List<News> list);
        void  onFailure(String str,Throwable e);
    }

    interface Presenter  {
        void loadData(int type, int page);
    }

    interface Model {
        void loadData( int type, OnLoadFirstDataListener listener,int page);
    }
}
