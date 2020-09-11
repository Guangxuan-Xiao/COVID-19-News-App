package com.java.yangjianke.ui.slideshow;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;
import com.java.yangjianke.R;
import com.java.yangjianke.data.Event;
import com.java.yangjianke.data.News;
import com.java.yangjianke.ui.news.NewsDao;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class EventViewHolder extends BaseViewHolder<Event> {
    private Context context;
    private TextView mTitle;
    private TextView mInfo;

    public EventViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.news_recycler_item);
        mTitle = $(R.id.news_recycler_title);
        mInfo = $(R.id.news_recycler_info);
        this.context = context;
    }

    @Override
    public void setData(final Event data) {
        if (data.title.length() <= 40){
            mTitle.setText(data.title);
        }
        else{
            mTitle.setText(data.title.substring(0, 40) + "......");
        }
        mInfo.setText(data.date + "  影响力：" + data.influence);
    }
}
