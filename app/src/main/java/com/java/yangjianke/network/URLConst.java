package com.example.covid_news.network;


import java.util.Locale;

public class URLConst {
    public static final String EVENTS_TEMPLATE = "https://covid-dashboard.aminer.cn/api/events/list?type=%s&page=%d&size=%d";
    public static final String EPIDEMIC_URL = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";
    public static final String ENTITY_QUERY_TEMPLATE = "https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity=%s";
    public static final String SCHOLAR_URL = "https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";

    public String getNewsURL(int page, int size) {
        return String.format(Locale.getDefault(), EVENTS_TEMPLATE, "news", page, size);
    }

    public String getPaperURL(int page, int size) {
        return String.format(Locale.getDefault(), EVENTS_TEMPLATE, "paper", page, size);
    }

    public String getEpidemicURL() {
        return EPIDEMIC_URL;
    }

    public String getEntityQueryURL(String entity) {
        return String.format(Locale.getDefault(), ENTITY_QUERY_TEMPLATE, entity);
    }

    public String getScholarURL() {
        return SCHOLAR_URL;
    }
}
