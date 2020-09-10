package com.java.yangjianke.ui.entity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.java.yangjianke.R;
import com.java.yangjianke.data.Entity;
import com.java.yangjianke.network.DownloadImageTask;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.Map;

public class PropertyViewHolder extends BaseViewHolder<String> {
    private Context context;
    private TextView mTitle;
    private TextView mDescription;

    public PropertyViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.property_item);
        mTitle = $(R.id.property_title);
        mDescription = $(R.id.property_detail);
        this.context = context;
    }

    @Override
    public void setData(final String data) {
        String[] res = data.split("dddddd");
        mTitle.setText(res[0]);
        mDescription.setText(res[1]);
    }
}
