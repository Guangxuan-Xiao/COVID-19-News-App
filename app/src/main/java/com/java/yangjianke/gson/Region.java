package com.example.covid_news.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Region {
    public String name;
    public String begin;
    public List<List<Integer>> data;
    //[COUNTRY|PROVINCE|COUNTY]:
    //    {
    //        "begin":"YYYY-mm-dd",
    //            "data": [
    //                  [CONFIRMED, SUSPECTED, CURED, DEAD, SEVERE, RISK, inc24]
    //     ]}
}
