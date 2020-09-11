package com.java.yangjianke.ui.news;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.java.yangjianke.R;
import com.java.yangjianke.data.DataBase;
import com.java.yangjianke.data.News;
import com.java.yangjianke.data.Paper;
import com.java.yangjianke.ui.news.contract.NewsContract;
import com.java.yangjianke.util.PixelUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NewsSearchActivity extends AppCompatActivity {
    @BindView(R.id.searchResultText)
    TextView mTextView;
    private NewsAdapter adapter;
    private NewsContract.Presenter mPresenter;
    private NewsDao dao;
    @BindView(R.id.searchResultRecycler)
    EasyRecyclerView mRecyclerView;
    @BindView(R.id.toolbarNewsSearch)
    Toolbar mToolbar;
    @BindView(R.id.title_search)
    TextView mTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_search);
        ButterKnife.bind(this);
        dao = new NewsDao(this);
        Activity a = this;
        mTextView.setText("正在搜索中……");
        mTitle.setText("新闻搜索");
        if (dao.getCount() < 1000){
            Handler handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 123) {
                        setSearchResult();
                    }
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DataBase db = new DataBase(a);
                    List<News> newsList = db.getNewsList(1, 1000);
                    for (News piece: newsList){
                        boolean exist = dao.searchNews(piece.getUrl());
                        if (!exist){
                            dao.addCache(piece, 0);
                        }
                    }
                    handler.sendEmptyMessage(123);
                }
            }).start();
            setSearchResult();
        }
        else{
            setSearchResult();
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<String> data = new ArrayList<String>();
                data.add(adapter.getAllData().get(position).getTitle());
                data.add(adapter.getAllData().get(position).getContent());
                data.add(adapter.getAllData().get(position).getDate());
                data.add(adapter.getAllData().get(position).getSource());
                data.add(adapter.getAllData().get(position).getUrl());
                ContentValues values = new ContentValues();
                values.put("visited", 1);
                dao.updateCache(values, "title=?",
                        new String[] {adapter.getAllData().get(position).getTitle()});
                Intent intent = new Intent(a, NewsDetailsActivity.class);
                //用Bundle携带数据
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data", data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void setSearchResult(){
        String searchText = this.getIntent().getStringExtra("searchText");
        List<News> searchResult = dao.search(searchText);
        mTextView.setText("搜索新闻：" + searchText + "\n共搜索到结果" +
                String.valueOf(searchResult.size()) + "条");
        adapter = new NewsAdapter(this);
        SpaceDecoration itemDecoration = new SpaceDecoration((int) PixelUtil.convertDpToPixel(8, this));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.addAll(searchResult);
        mRecyclerView.setAdapter(adapter);
    }

}
