package com.java.yangjianke.data;

import java.net.URL;
import java.util.List;

public class News extends Data {
    public String content;
    public String date;
    public String source;
    public String time;
    public String title;
    public List<URL> urls;

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

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSource() {
        return source;
    }

    public List<URL> getUrls() {
        return urls;
    }

    public String getUrl() {
        if (urls.size() == 0){
            return null;
        }
        return urls.get(0).toString();
    }
}
