package com.java.yangjianke.data;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.HashMap;
import java.util.List;


public class Entity extends Data {
    public String label;
    public float hot;
    public URL url;

    class Abstract {
        String baidu;

        class COVID {
            public HashMap<String, String> properties;

            class Relation {
                public String relation;
                public URL url;
                public String label;
                public boolean forward;

                @Override
                public String toString() {
                    return "Relation{" +
                            "\nrelation='" + relation + '\'' +
                            ", \nurl=" + url +
                            ", \nlabel='" + label + '\'' +
                            ", \nforward=" + forward +
                            "\n}";
                }
            }

            public List<Relation> relations;

            @Override
            public String toString() {
                return "COVID{" +
                        "\nproperties=" + properties +
                        ", \nrelations=" + relations +
                        "\n}";
            }
        }

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
}
