package com.example.covid_news.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;

public class Relation implements Parcelable {
    public String relation;
    public URL url;
    public String label;
    public boolean forward;

    protected Relation(Parcel in) {
        relation = in.readString();
        try {
            url = new URL(in.readString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        label = in.readString();
        forward = in.readInt() == 1;
    }

    public static final Creator<Relation> CREATOR = new Creator<Relation>() {
        @Override
        public Relation createFromParcel(Parcel in) {
            return new Relation(in);
        }

        @Override
        public Relation[] newArray(int size) {
            return new Relation[size];
        }
    };

    @Override
    public String toString() {
        return "Relation{" +
                "\nrelation='" + relation + '\'' +
                ", \nurl=" + url +
                ", \nlabel='" + label + '\'' +
                ", \nforward=" + forward +
                "\n}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(relation);
        parcel.writeString(url.toString());
        parcel.writeString(label);
        parcel.writeInt(forward?1:0);
    }
}
