package com.java.yangjianke.data;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.List;

public class Event extends Data {
    @SerializedName("_id")
    public String id;
    public int cluster;
    class Entity {
        public String label;
        public URL url;

        @Override
        public String toString() {
            return "Entity{" +
                    "\nlabel='" + label + '\'' +
                    ", \nurl=" + url +
                    "\n}";
        }
    }

    public String date;
    public float influence;

    class RelatedEvent {
        public String id;
        public float score;

        @Override
        public String toString() {
            return "RelatedEvent{" +
                    "\nid='" + id + '\'' +
                    ", \nscore=" + score +
                    "\n}";
        }
    }

    @SerializedName("related_events")
    public List<RelatedEvent> relatedEvents;
    @SerializedName("seg_text")
    public String segText;
    public String title;
    public List<URL> urls;

    @Override
    public String toString() {
        return "Event{" +
                "\nid='" + id + '\'' +
                ", \ndate='" + date + '\'' +
                ", \ninfluence=" + influence +
                ", \nrelatedEvents=" + relatedEvents +
                ", \nsegText='" + segText + '\'' +
                ", \ntitle='" + title + '\'' +
                ", \nurls=" + urls +
                "\n}";
    }
}
