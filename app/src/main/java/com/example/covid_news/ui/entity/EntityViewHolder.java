package com.example.covid_news.ui.entity;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.covid_news.R;
import com.example.covid_news.data.Entity;
import com.example.covid_news.data.News;
import com.example.covid_news.network.DownloadImageTask;
import com.example.covid_news.ui.news.NewsDao;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class EntityViewHolder extends BaseViewHolder<Entity> {

    private Context context;
    private TextView mTitle;
    private ImageView mImg;

    public EntityViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.entity_recycler_item);
        mTitle = $(R.id.entity_name);
        mImg = $(R.id.entity_image);
        this.context = context;
    }

    @Override
    public void setData(final Entity data) {
        mTitle.setText(data.label);
        System.out.println(data.img);
        if (data.img != null)
            new DownloadImageTask(mImg).execute(data.img.toString());
        else
            mImg.setImageResource(R.drawable.default_img);
    }
}

