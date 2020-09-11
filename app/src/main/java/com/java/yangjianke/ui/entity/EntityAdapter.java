package com.java.yangjianke.ui.entity;

import android.content.Context;
import android.view.ViewGroup;
import com.java.yangjianke.data.Entity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class EntityAdapter extends RecyclerArrayAdapter<Entity> {

    public EntityAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new EntityViewHolder(parent, this.getContext());
    }
}
