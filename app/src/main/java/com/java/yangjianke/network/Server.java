package com.java.yangjianke.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;


public class Server {
    private static URLConst urlConst = new URLConst();
    private static final OkHttpClient client = new OkHttpClient();

    private String getJsonStr(String url) {
        final Request request = new Request.Builder().url(url).build();
        String json = null;
        try (Response response = client.newCall(request).execute()) {
            json = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String getNewsListJson(int page, int size) {
        return getJsonStr(urlConst.getNewsURL(page, size));
    }

    public String getPaperListJson(int page, int size) {
        return getJsonStr(urlConst.getPaperURL(page, size));
    }

    public String getEpidemicJson() {
        return getJsonStr(urlConst.getEpidemicURL());
    }

    public String getExpertJson() {
        return getJsonStr(urlConst.getScholarURL());
    }

    public String getEntityQueryJson(String entity) {
        return getJsonStr(urlConst.getEntityQueryURL(entity));
    }
}
