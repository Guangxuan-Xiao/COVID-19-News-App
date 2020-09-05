import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class RegionParser {
    public static void main(String args[]) throws IOException {
        File file = new File("epidemic.json");
        long fileLength = file.length();
        byte[] fileContent = new byte[(int) fileLength];
        FileInputStream in = new FileInputStream(file);
        in.read(fileContent);
        String json = new String(fileContent);
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject regions = jsonParser.parse(json).getAsJsonObject();
        for (String name : regions.keySet()) {
            JsonObject regionJsonObject = regions.get(name).getAsJsonObject();
            Region region = new Region(name);
            region.begin = regionJsonObject.get("begin").getAsString();
            JsonArray dataJsonArray = regionJsonObject.get("data").getAsJsonArray();
            Type dataType = new TypeToken<List<List<Integer>>>() {
            }.getType();
            region.data = gson.fromJson(dataJsonArray, dataType);
            System.out.println(region);
            return;
        }
    }
}