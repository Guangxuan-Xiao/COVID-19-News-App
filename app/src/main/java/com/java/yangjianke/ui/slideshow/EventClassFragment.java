package com.java.yangjianke.ui.slideshow;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.java.yangjianke.R;
import com.java.yangjianke.data.Event;
import com.java.yangjianke.data.News;
import com.java.yangjianke.ui.news.NewsAdapter;
import com.java.yangjianke.ui.news.NewsClassFragment;
import com.java.yangjianke.ui.news.NewsDao;
import com.java.yangjianke.ui.news.NewsDetailsActivity;
import com.java.yangjianke.ui.news.presenter.NewsPresenter;
import com.java.yangjianke.util.PixelUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class EventClassFragment extends Fragment {

    private EventAdapter adapter;

    public static EventClassFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        EventClassFragment fragment = new EventClassFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private int pageIndex = 1;
    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_class, container, false);
        ButterKnife.bind(this, view);
        adapter = new EventAdapter(getContext());
        recyclerView.setAdapter(adapter);

        //添加边框
        SpaceDecoration itemDecoration = new SpaceDecoration((int)PixelUtil.convertDpToPixel(8, getContext()));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
        AssetManager assetManager = getActivity().getAssets();
        try {
            InputStream is = assetManager.open("clustered_events.json");
            String jsonData = readDataFromInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    public String readDataFromInputStream(InputStream is) {
        BufferedInputStream bis = new BufferedInputStream(is);

        String str = "", s = "";

        int c = 0;
        byte[] buf = new byte[64];
        while (true) {
            try {
                c = bis.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (c == -1)
                break;
            else {
                try {
                    s = new String(buf, 0, c, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                str += s;
            }
        }

        try {
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }


}
