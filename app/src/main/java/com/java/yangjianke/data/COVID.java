package com.java.yangjianke.data;

import java.util.HashMap;
import java.util.List;

public class COVID {
    public HashMap<String, String> properties;
    public List<Relation> relations;
    @Override
    public String toString() {
        return "COVID{" +
                "\nproperties=" + properties +
                ", \nrelations=" + relations +
                "\n}";
    }
}
