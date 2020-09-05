package com.example.covid_news.ui.news.contract;

import com.example.covid_news.gson.NewsGson;
import java.util.List;

public interface NewsContract {


    interface View{
        void returnData(List<NewsGson.NewslistBean> datas);
    }

    interface OnLoadFirstDataListener{
        void  onSuccess(List<NewsGson.NewslistBean> list);
        void  onFailure(String str,Throwable e);
    }

    interface Presenter  {
        void loadData(int type, int page);
    }

    interface Model {
        void loadData( int type, OnLoadFirstDataListener listener,int page);
    }
}
