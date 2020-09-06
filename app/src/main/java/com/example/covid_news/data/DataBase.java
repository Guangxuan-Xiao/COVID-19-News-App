package com.example.covid_news.data;

import com.example.covid_news.network.Server;

import java.util.List;

public class DataBase {
    private Parser parser;
    private Server server;

    public List<News> getNewsList(int page, int size) {
        return parser.parseNewsList(server.getNewsListJson(page, size));
    }

    public List<Paper> getPaperList(int page, int size) {
        return parser.parsePaperList(server.getPaperListJson(page, size));
    }

    public List<Region> getRegionList() {
        return parser.parseEpidemic(server.getEpidemicJson());
    }


}
