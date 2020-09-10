package com.example.covid_news.data;

import com.google.gson.annotations.SerializedName;

public class Abstract {
    String baidu;
    @SerializedName("COVID")
    COVID covid;

    @Override
    public String toString() {
        return "Abstract{" +
                "\nbaidu='" + baidu + '\'' +
                ", \ncovid=" + covid +
                "\n}";
    }
}
