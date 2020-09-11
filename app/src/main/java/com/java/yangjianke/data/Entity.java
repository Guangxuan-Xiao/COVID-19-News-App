package com.java.yangjianke.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Entity extends Data {
    public String label;
    public float hot;
    public URL url;

//    public class Abstract {
//        String baidu;
//
//        public class COVID {
//            public HashMap<String, String> properties;
//
//            public class Relation {
//                public String relation;
//                public URL url;
//                public String label;
//                public boolean forward;
//
//                protected Relation(Parcel in) {
//                    relation = in.readString();
//                    label = in.readString();
//                    forward = in.readByte() != 0;
//                }
//
//                @Override
//                public String toString() {
//                    return "Relation{" +
//                            "\nrelation='" + relation + '\'' +
//                            ", \nurl=" + url +
//                            ", \nlabel='" + label + '\'' +
//                            ", \nforward=" + forward +
//                            "\n}";
//                }
//            }
//
//            public List<Relation> relations;
//
//            @Override
//            public String toString() {
//                return "COVID{" +
//                        "\nproperties=" + properties +
//                        ", \nrelations=" + relations +
//                        "\n}";
//            }
//        }
//
//        @SerializedName("COVID")
//        COVID covid;
//
//        @Override
//        public String toString() {
//            return "Abstract{" +
//                    "\nbaidu='" + baidu + '\'' +
//                    ", \ncovid=" + covid +
//                    "\n}";
//        }
//    }

    public Abstract abstractInfo;
    public URL img;

    @Override
    public String toString() {
        return "Entity{" +
                "\nlabel='" + label + '\'' +
                ", \nhot=" + hot +
                ", \nurl=" + url +
                ", \nabstractInfo=" + abstractInfo +
                ", \nimg=" + img +
                "\n}";
    }

    public String getInfo(){
        return abstractInfo.baidu;
    }

    public List<Relation> getRelations(){
        return abstractInfo.covid.relations;
    }

    public ArrayList<String> getProperties(){
        ArrayList<String> ret = new ArrayList<String>();
        for (Map.Entry<String, String> p: abstractInfo.covid.properties.entrySet()){
            ret.add(p.getKey() + "dddddd" + p.getValue());
        }
        return ret;
    }
}
