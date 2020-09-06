package com.example.covid_news.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class NewsParser {
    public static void main(String args[]) throws IOException {
        File file = new File("news.json");
        long fileLength = file.length();
        byte[] fileContent = new byte[(int) fileLength];
        FileInputStream in = new FileInputStream(file);
        in.read(fileContent);
        String json = new String(fileContent);
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject query = jsonParser.parse(json).getAsJsonObject();
        Type type = new TypeToken<List<News>>(){}.getType();
        List<News> newsList = gson.fromJson(query.getAsJsonArray("data"), type);
        for (int i = 0; i < newsList.size(); ++i) {
            System.out.println(newsList.get(i));
        }
    }
}