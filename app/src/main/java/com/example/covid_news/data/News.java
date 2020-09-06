package com.example.covid_news.data;

import java.net.URL;
import java.util.List;

public class News {
    private String content;
    private String date;
    private String source;
    private String time;
    private String title;
    private List<URL> urls;

    @Override
    public String toString() {
        return "News{\n" +
                "\ncontent='" + content + '\'' +
                ", \ndate='" + date + '\'' +
                ", \nsource='" + source + '\'' +
                ", \ntime='" + time + '\'' +
                ", \ntitle='" + title + '\'' +
                ", \nurls=" + urls +
                "\n}";
    }

    public String getTitle(){ return title; }
    public String getContent(){ return content; }
    public String getDate(){ return date; }
    public String getTime(){ return time; }
    public String getSource(){ return source; }
    public List<URL> getUrls(){ return urls; }
}
