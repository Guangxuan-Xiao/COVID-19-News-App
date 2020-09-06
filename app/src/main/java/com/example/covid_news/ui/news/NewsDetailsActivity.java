package com.example.covid_news.ui.news;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
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

    @BindView(R.id.Title)
    TextView title;
    @BindView(R.id.Info)
    TextView info;
    @BindView(R.id.Content)
    TextView content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        final ArrayList<String> data = bundle.getStringArrayList("data");

        title.setText(data.get(0));
        info.setText(data.get(2) + "  来源：" + data.get(3));
        content.setText(data.get(1));

    }
}
