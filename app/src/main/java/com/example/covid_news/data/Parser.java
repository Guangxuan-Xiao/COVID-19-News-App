package com.example.covid_news.data;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {
    private static Gson gson = new Gson();
    private static JsonParser jsonParser = new JsonParser();

    public List<News> parseNewsList(String json) {
        JsonObject query = jsonParser.parse(json).getAsJsonObject();
        Type type = new TypeToken<List<News>>() {
        }.getType();
        List<News> newsList = gson.fromJson(query.getAsJsonArray("data"), type);
        return newsList;
    }

    public List<Paper> parsePaperList(String json) {
        JsonObject query = jsonParser.parse(json).getAsJsonObject();
        Type type = new TypeToken<List<Paper>>() {
        }.getType();
        List<Paper> paperList = gson.fromJson(query.getAsJsonArray("data"), type);
        return paperList;
    }

    public List<Region> parseEpidemic(String json) {
        JsonObject regions = jsonParser.parse(json).getAsJsonObject();
        List<Region> regionList = new ArrayList<>();
        for (Map.Entry<String, JsonElement> en : regions.entrySet()) {
            String name = en.getKey();
            JsonObject regionJsonObject = regions.get(name).getAsJsonObject();
            Region region = new Region(name);
            region.begin = regionJsonObject.get("begin").getAsString();
            JsonArray dataJsonArray = regionJsonObject.get("data").getAsJsonArray();
            Type dataType = new TypeToken<List<List<Integer>>>() {
            }.getType();
            region.data = gson.fromJson(dataJsonArray, dataType);
            regionList.add(region);
        }
        return regionList;
    }


}
