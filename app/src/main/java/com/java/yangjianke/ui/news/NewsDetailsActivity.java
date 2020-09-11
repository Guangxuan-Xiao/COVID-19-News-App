package com.java.yangjianke.ui.news;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.java.yangjianke.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.java.yangjianke.weibo.ShareActivity;

public class NewsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbarNewsDetail)
    Toolbar toolbar;
    @BindView(R.id.web_text)
    WebView webView;
    @BindView(R.id.title_detail)
    TextView mTitle;
    private String url;
    private String content;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        final ArrayList<String> data = bundle.getStringArrayList("data");

//        title.setText(data.get(0));
//        info.setText(data.get(2) + "  来源：" + data.get(3));
//        content.setText(data.get(1));
        toolbar.setTitle("新闻详情");
        toolbar.setTitleTextColor(Color.WHITE);
        mTitle.setText("新闻详情");

        setSupportActionBar(toolbar);
//        设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("新闻详情");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //新页面接收数据
        content = data.get(1);
        url = data.get(4);
        Log.e("url", url);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_button:
                Intent intent=new Intent(NewsDetailsActivity.this, ShareActivity.class);
                ArrayList<String> data = new ArrayList<String>();
                data.add(url);
                data.add(content);
                //用Bundle携带数据
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data", data);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
