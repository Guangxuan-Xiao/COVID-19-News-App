package com.example.covid_news.ui.expert.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.covid_news.data.DataBase;
import com.example.covid_news.data.Expert;
import com.example.covid_news.data.News;
import com.example.covid_news.data.Paper;
import com.example.covid_news.ui.expert.contract.ExpertContract;
import com.example.covid_news.util.NetworkUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpertModel implements ExpertContract.Model {
    private final DataBase db;
    private Context context;
    List<Expert> expertList;
    Handler handler;

    public ExpertModel(Context context) {
        this.context = context;
        db = new DataBase(context);
    }

    @Override
    public void loadData(final ExpertContract.OnLoadFirstDataListener listener) {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 123) {
                    listener.onSuccess(expertList);
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                expertList = db.getExpertList();
                handler.sendEmptyMessage(123);
            }
        }).start();
    }
}

