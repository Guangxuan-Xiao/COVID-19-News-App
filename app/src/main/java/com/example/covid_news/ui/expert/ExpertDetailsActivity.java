package com.example.covid_news.ui.expert;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.covid_news.R;
import com.example.covid_news.network.DownloadImageTask;

import java.util.ArrayList;

public class ExpertDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.position)
    TextView position;
    @BindView(R.id.affiliation)
    TextView affiliation;
    @BindView(R.id.work)
    TextView work;
    @BindView(R.id.edu)
    TextView edu;
    @BindView(R.id.bio)
    TextView bio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_detail);
        ButterKnife.bind(this);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        final ArrayList<String> data = bundle.getStringArrayList("data");
        new DownloadImageTask(avatar).execute(data.get(0));
        if (!data.get(1).equals(""))
            name.setText(data.get(1));
        else name.setText(data.get(2));
        position.setText(data.get(3));
        affiliation.setText(data.get(4));
        work.setText(data.get(5));
        edu.setText(data.get(6));
        bio.setText(data.get(7));
        toolbar.setTitle("学者详情");
        setSupportActionBar(toolbar);
//        设置返回箭头        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
