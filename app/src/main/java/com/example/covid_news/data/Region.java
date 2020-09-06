package com.example.covid_news.data;

import java.net.URL;
import java.util.List;

public class Region {
    public String name;
    public String begin;
    public List<List<Integer>> data;

    Region(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Region{" +
                "\nname='" + name + "\'" +
                ", \nbegin='" + begin + "\'" +
                ", \ndata=" + data +
                "\n}";
    }
    //[COUNTRY|PROVINCE|COUNTY]:
    //    {
    //        "begin":"YYYY-mm-dd",
    //            "data": [
    //                  [CONFIRMED, SUSPECTED, CURED, DEAD, SEVERE, RISK, inc24]
    //     ]}
}
