package com.java.yangjianke.ui.expert.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.java.yangjianke.data.DataBase;
import com.java.yangjianke.data.Expert;
import com.java.yangjianke.data.News;
import com.java.yangjianke.data.Paper;
import com.java.yangjianke.ui.expert.contract.ExpertContract;
import com.java.yangjianke.util.NetworkUtil;

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

