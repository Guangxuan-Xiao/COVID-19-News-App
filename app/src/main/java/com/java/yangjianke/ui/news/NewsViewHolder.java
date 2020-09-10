package com.example.covid_news.ui.news;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.covid_news.R;
import com.example.covid_news.data.News;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class NewsViewHolder extends BaseViewHolder<News> {

    private Context context;
    private TextView mTitle;
    private TextView mInfo;

    public NewsViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.news_recycler_item);
        mTitle = $(R.id.news_recycler_title);
        mInfo = $(R.id.news_recycler_info);
        this.context = context;
    }

    @Override
    public void setData(final News data) {
        mTitle.setText(data.getTitle());
        NewsDao dao = new NewsDao(context);
        int visited = 0;
        try{
            visited = Integer.parseInt(dao.viewCache("title=?",
                    new String[]{data.getTitle()}).get("visited"));
        } catch(Exception e){
            //do nothing
        }
        if (visited == 1){
            mTitle.setTextColor(Color.BLUE);
        }
        if (data.getSource() == null){
            mInfo.setText(data.getTime());
        }
        else {
            mInfo.setText(data.getTime() + " 来源：" + data.getSource());
        }
    }
}
