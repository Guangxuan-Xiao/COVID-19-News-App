package com.example.covid_news.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.covid_news.network.Server;

import java.io.*;
import java.util.List;

public class DataBase {
    private Parser parser;
    private Server server;
    private Context context;

    public DataBase(Context context) {
        parser = new Parser();
        server = new Server();
        this.context = context;
    }

    public List<News> getNewsList(int page, int size) {
        return parser.parseNewsList(server.getNewsListJson(page, size));
    }

    public List<Paper> getPaperList(int page, int size) {
        return parser.parsePaperList(server.getPaperListJson(page, size));
    }

    public List<Entity> getEntityList(String entity) {
        return parser.parseEntityList(server.getEntityQueryJson(entity));
    }

    public List<Expert> getExpertList() {
        return parser.parseExpertList(server.getExpertJson());
    }



    public List<Region> getRegionListFromLocal() {
        File f = new File(context.getFilesDir(), "epidemic.txt");
        Long fileLength = f.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        try {
            FileInputStream is = new FileInputStream(f);
            is.read(fileContent);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = new String(fileContent);
        return parser.parseEpidemic(str);
    }

    public List<Region> getRegionList() {
        File f = new File(context.getFilesDir(), "epidemic.txt");
        try {
            FileOutputStream os = new FileOutputStream(f);
            String str = server.getEpidemicJson();
            os.write(str.getBytes());
            Log.i("Get from Internet", "1");
            os.close();
            return parser.parseEpidemic(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
//        Long fileLength = f.length();
//        byte[] fileContent = new byte[fileLength.intValue()];
//        try {
//            FileInputStream is = new FileInputStream(f);
//            is.read(fileContent);
//            is.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String str = new String(fileContent);
//        return parser.parseEpidemic(str);
    }
}
