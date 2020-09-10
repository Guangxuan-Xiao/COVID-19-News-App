package com.java.yangjianke.ui.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.java.yangjianke.data.RelatedEntity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

public class EntityRelationAdapter extends RecyclerArrayAdapter<RelatedEntity> {

    public EntityRelationAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new EntityRelationViewHolder(parent, this.getContext());
    }
}
