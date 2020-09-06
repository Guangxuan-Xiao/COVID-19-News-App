package com.example.covid_news.data;

import java.net.URL;
import java.util.List;

public class Scholar {
    public URL avatar;
    public String name;
    public String name_zh;

    class Indices {
        public float activity;
        public float citation;
        public float diversity;
        public int gindex;
        public int hindex;
        public float newStar;
        public int pubs;
        public float risingStar;
        public float sociability;

        @Override
        public String toString() {
            return "Indices{" +
                    "\nactivity=" + activity +
                    ", \ncitation=" + citation +
                    ", \ndiversity=" + diversity +
                    ", \ngindex=" + gindex +
                    ", \nhindex=" + hindex +
                    ", \nnewStar=" + newStar +
                    ", \npubs=" + pubs +
                    ", \nrisingStar=" + risingStar +
                    ", \nsociability=" + sociability +
                    "\n}";
        }
    }
    public Indices indices;
    class Profile {
        public String address;
        public String affiliation;
        public String affiliation_zh;
        public String bio;
        public String edu;
        public URL homepage;
        public String position;
        public String work;

        @Override
        public String toString() {
            return "Profile{" +
                    "\naddress='" + address + '\'' +
                    ", \naffiliation='" + affiliation + '\'' +
                    ", \naffiliation_zh='" + affiliation_zh + '\'' +
                    ", \nbio='" + bio + '\'' +
                    ", \nedu='" + edu + '\'' +
                    ", \nhomepage=" + homepage +
                    ", \nposition='" + position + '\'' +
                    ", \nwork='" + work + '\'' +
                    "\n}";
        }
    }

    public Profile profile;
    public List<String> tags;
    public boolean is_passedaway;

    @Override
    public String toString() {
        return "Scholar{" +
                "\navatar=" + avatar +
                ", \nname='" + name + '\'' +
                ", \nname_zh='" + name_zh + '\'' +
                ", \nindices=" + indices +
                ", \nprofile=" + profile +
                ", \ntags=" + tags +
                ", \nis_passedaway=" + is_passedaway +
                "\n}";
    }
}
