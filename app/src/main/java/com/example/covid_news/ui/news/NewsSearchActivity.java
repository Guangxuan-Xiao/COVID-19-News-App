package com.example.covid_news.ui.news;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.covid_news.R;
import com.example.covid_news.data.News;
import com.example.covid_news.ui.news.contract.NewsContract;
import com.example.covid_news.util.PixelUtil;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_search);
        ButterKnife.bind(this);
        dao = new NewsDao(this);
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
        Activity a = this;

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

}
