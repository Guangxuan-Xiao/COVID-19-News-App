package com.java.yangjianke.ui.slideshow;

import android.content.Context;
import android.view.ViewGroup;
import com.java.yangjianke.data.Event;
import com.java.yangjianke.ui.news.NewsViewHolder;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class EventAdapter extends RecyclerArrayAdapter<Event> {
    public EventAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(parent, this.getContext());
    }
}
