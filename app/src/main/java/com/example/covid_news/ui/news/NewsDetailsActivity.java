package com.example.covid_news.ui.news;

import android.app.Activity;
import android.os.Bundle;
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
import com.example.covid_news.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.web_text)
    WebView webText;
    @BindView(R.id.ivImage)
    ImageView ivImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        toolbar.setTitle("新闻详情");

        setSupportActionBar(toolbar);
//        设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        final ArrayList<String> data = bundle.getStringArrayList("data");
        Log.e("url", data.get(1));

        WebSettings webSettings = webText.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webText.loadUrl(data.get(1));

        webText.setWebViewClient(new WebViewClient() {

            //已加载
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //编写 javaScript方法
//                String javascript = "javascript:function clearBody(){" +
//                        "document.getElementsByTagName('head')[0].innerHTML=\"\";" +
//                        "}";

                //创建方法
                //view.loadUrl(javascript);

                //加载方法
                // view.loadUrl("javascript:clearBody();");


            }

        });

        Glide.with(this)
                .load(data.get(0)).error(R.mipmap.ic_launcher)
                .fitCenter().into(ivImage);

    }
}
