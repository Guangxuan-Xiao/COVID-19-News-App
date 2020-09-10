package com.example.covid_news.data;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.List;

public class Paper extends Data {
    class Author {
        String name;
        String id;

        @Override
        public String toString() {
            return "Author{" +
                    "\nname='" + name + '\'' +
                    ", \nid='" + id + '\'' +
                    "\n}";
        }
    }

    public String title;
    public String content;
    @SerializedName("seg_text")
    public String segText;
    public String date;
    public String time;
    public List<Author> authors;
    public String doi;
    public URL pdf;
    public List<URL> urls;

    @Override
    public String toString() {
        return "Paper{" +
                "\ntitle='" + title + '\'' +
                ", \ncontent='" + content + '\'' +
                ", \nseg_text='" + segText + '\'' +
                ", \ndate='" + date + '\'' +
                ", \ntime='" + time + '\'' +
                ", \nauthors=" + authors +
                ", \ndoi='" + doi + '\'' +
                ", \npdf=" + pdf +
                ", \nurls=" + urls +
                "\n}";
    }

    public News toNews(){
        News news = new News();
        news.urls = urls;
        news.content = content;
        news.title = title;
        news.time = time;
        news.date = date;
        news.source = null;
        return news;
    }
}
