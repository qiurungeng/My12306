package com.example.neu.neuassigment.util;

import android.content.Context;

import com.example.neu.neuassigment.bean.Station;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AssetsFileReadUtil {

    public static List<Station> getAllStations(Context context){
        List<Station> stations=new ArrayList<>();
        try {
            Context appContext = context;
            InputStreamReader inputReader = new InputStreamReader( appContext.getResources().getAssets().open("12306.json") );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String readResult="";
            while((line = bufReader.readLine()) != null)
                readResult += line;
            JsonParser jsonParser=new JsonParser();
            JsonObject data=jsonParser.parse(readResult).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> set=data.entrySet();

            Station forItem;
            for (Map.Entry<String,JsonElement> entry:set){
                String name=entry.getKey();
                String code=entry.getValue().toString().replace("\"","");
                forItem=new Station();
                forItem.setName(name);
                forItem.setCode(code);
                forItem.save();
                stations.add(forItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stations;
    }
}
