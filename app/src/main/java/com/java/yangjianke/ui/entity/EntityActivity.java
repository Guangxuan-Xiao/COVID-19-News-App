package com.java.yangjianke.ui.entity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.java.yangjianke.R;
import com.java.yangjianke.data.DataBase;
import com.java.yangjianke.data.Entity;
import com.java.yangjianke.data.News;
import com.java.yangjianke.ui.news.NewsAdapter;
import com.java.yangjianke.ui.news.NewsDao;
import com.java.yangjianke.ui.news.NewsDetailsActivity;
import com.java.yangjianke.ui.news.contract.NewsContract;
import com.java.yangjianke.util.PixelUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.ArrayList;
import java.util.List;

public class EntityActivity extends AppCompatActivity {

    @BindView(R.id.entityResultText)
    TextView mTextView;
    private EntityAdapter adapter;
    @BindView(R.id.entityResultRecycler)
    EasyRecyclerView mRecyclerView;
    @BindView(R.id.toolbarEntity)
    Toolbar mToolbar;
    @BindView(R.id.title_entity)
    TextView mTitle;
    private String searchText;

    List<Entity> entityList;

    public void returnData(){
        mTextView.setText("搜索实体：" + searchText + "\n共搜索到结果" +
                String.valueOf(entityList.size()) + "条");
        adapter.addAll(entityList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);
        ButterKnife.bind(this);
        searchText = this.getIntent().getStringExtra("searchText");
        Handler handler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 123) {
                    Log.i("Read data", "Entity");
                    returnData();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataBase db = new DataBase(getApplicationContext());
                entityList = db.getEntityList(searchText);
                handler.sendEmptyMessage(123);
            }
        }).start();
        mTitle.setText("实体搜索");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new EntityAdapter(this);
        SpaceDecoration itemDecoration = new SpaceDecoration((int) PixelUtil.convertDpToPixel(8, this));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        Activity a = this;

        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<String> data = new ArrayList<String>();
                data.add(adapter.getAllData().get(position).label);
//                data.add(adapter.getAllData().get(position).abstractInfo);
//                data.add(adapter.getAllData().get(position).getDate());
//                data.add(adapter.getAllData().get(position).getSource());
//                data.add(adapter.getAllData().get(position).getUrl());
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
