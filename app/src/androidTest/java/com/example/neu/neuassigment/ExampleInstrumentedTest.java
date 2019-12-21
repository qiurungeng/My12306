package com.example.neu.neuassigment;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.neu.neuassigment.db.Station;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.neu.neuassigment", appContext.getPackageName());
    }

    @Test
    public void readStationJsonFromAssetsAndSaveToDB(){
        try {
            Context appContext = InstrumentationRegistry.getTargetContext();
            InputStreamReader inputReader = new InputStreamReader( appContext.getResources().getAssets().open("12306.json") );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            Log.d("测试啦",Result);

            JsonParser jsonParser=new JsonParser();
            JsonObject data=jsonParser.parse(Result).getAsJsonObject();

            Set<Map.Entry<String, JsonElement>> set=data.entrySet();
            Station forItem;
            for (Map.Entry<String,JsonElement> entry:set){
                String name=entry.getKey();
                String code=entry.getValue().toString().replace("\"","");
                Log.d("将保存站名",entry.getKey()+"|"+entry.getValue().toString().replace("\"",""));
                forItem=new Station();
                forItem.setName(name);
                forItem.setCode(code);
                forItem.save();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIfStationInfoAreSaved(){
        List<Station> stations= DataSupport.findAll(Station.class);
        for (Station station : stations) {
            Log.d("已保存的车站",station.toString());
        }
    }

}
