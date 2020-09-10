package com.example.covid_news.ui.entity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.covid_news.R;
import com.example.covid_news.data.Entity;
import com.example.covid_news.data.RelatedEntity;
import com.example.covid_news.network.DownloadImageTask;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class EntityRelationViewHolder extends BaseViewHolder<RelatedEntity> {

    private Context context;
    private TextView mTitle;
    private ImageView mImg;
    private TextView mRelation;

    public EntityRelationViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.expert_recycler_item);
        mTitle = $(R.id.name);
        mImg = $(R.id.avatar);
        mRelation = $(R.id.position);
        this.context = context;
    }

    @Override
    public void setData(final RelatedEntity data) {
        mTitle.setText(data.entity.label);
        mRelation.setText(data.relation);
        if (data.entity.img != null)
            new DownloadImageTask(mImg).execute(data.entity.img.toString());
        else
            mImg.setImageResource(R.drawable.default_img);
    }

}
