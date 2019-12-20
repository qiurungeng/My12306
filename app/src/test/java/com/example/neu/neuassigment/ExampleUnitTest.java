package com.example.neu.neuassigment;

import com.example.neu.neuassigment.gson.TrainDetail;
import com.example.neu.neuassigment.util.HandleRequestUtil;
import com.example.neu.neuassigment.util.HandleResponseUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testAPI(){

        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder =HttpUrl.parse("https://api.jisuapi.com/train/station2s")
                .newBuilder();
        urlBuilder.addQueryParameter("appkey","574743780fae33cd");
        urlBuilder.addQueryParameter("start", "沈阳");
        urlBuilder.addQueryParameter("end", "广州");
        urlBuilder.addQueryParameter("date", "2020-01-11");
        urlBuilder.addQueryParameter("ishigh","0");
        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();

        OkHttpClient client=new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();
            String responseData=response.body().string();
            System.out.println(responseData);
            JSONObject jsonObject=new JSONObject(responseData);
            System.out.println(jsonObject.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        System.out.println();

    }

    @Test
    public void testGson() throws FileNotFoundException {
        ArrayList<TrainDetail> trainDetailArrayList=new ArrayList<>();
        BufferedReader bufferedReader=new BufferedReader(
                new FileReader("C:\\Users\\Apollos\\Desktop\\example.json"));
        try {
            String response=bufferedReader.readLine();
            JsonParser parser=new JsonParser();
            JsonElement element=parser.parse(response);

            System.out.println(element instanceof JsonObject);

            if (element instanceof JsonObject){
                JsonObject jsonObject=element.getAsJsonObject();
                JsonObject result=jsonObject.getAsJsonObject("result");
                JsonArray trainDetails=result.getAsJsonArray("list");
                for (JsonElement trainDetail : trainDetails) {
                    TrainDetail ok=new Gson().fromJson(trainDetail.toString(),TrainDetail.class);
                    trainDetailArrayList.add(ok);
                }

                System.out.println(trainDetailArrayList.get(0).toString());

            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testRequestAndResponse(){
        String response = HandleRequestUtil
                .handleSearchTrainRequest("沈阳", "广州", "0", "2020-01-12");
        List<TrainDetail> trainDetails= HandleResponseUtil.handleSearchTrainResponse(response);
        for (TrainDetail trainDetail : trainDetails) {
            System.out.println(trainDetail);
        }

        /**
         * 测试样例输出数据(2019/12/19)
         * TrainDetail(trainno=T123, type=T, station=沈阳北, endstation=广州, departuretime=00:19, arrivaltime=10:48, sequenceno=3, costtime=1天10时29分, distance=0, isend=1, pricesw=null, pricetd=null, pricegr1=null, pricegr2=null, pricerw1=null, pricerw2=null, priceyw1=null, priceyw2=null, priceyw3=null, priceyd=null, priceed=null)
         * TrainDetail(trainno=Z235, type=Z, station=沈阳北, endstation=广州东, departuretime=02:05, arrivaltime=08:26, sequenceno=2, costtime=1天6时25分, distance=0, isend=1, pricesw=null, pricetd=null, pricegr1=null, pricegr2=null, pricerw1=null, pricerw2=null, priceyw1=null, priceyw2=null, priceyw3=null, priceyd=null, priceed=null)
         * TrainDetail(trainno=Z12, type=Z, station=沈阳北, endstation=广州东, departuretime=06:38, arrivaltime=13:50, sequenceno=1, costtime=1天7时0分, distance=0, isend=1, pricesw=null, pricetd=null, pricegr1=null, pricegr2=null, pricerw1=913.5, pricerw2=null, priceyw1=513.5, priceyw2=null, priceyw3=null, priceyd=null, priceed=null)
         * TrainDetail(trainno=Z13, type=Z, station=沈阳北, endstation=广州东, departuretime=06:50, arrivaltime=13:50, sequenceno=1, costtime=1天7时0分, distance=0, isend=1, pricesw=null, pricetd=null, pricegr1=null, pricegr2=null, pricerw1=null, pricerw2=null, priceyw1=null, priceyw2=null, priceyw3=null, priceyd=null, priceed=null)
         * TrainDetail(trainno=Z111, type=Z, station=沈阳北, endstation=广州, departuretime=15:59, arrivaltime=21:10, sequenceno=4, costtime=1天5时11分, distance=0, isend=0, pricesw=null, pricetd=null, pricegr1=null, pricegr2=null, pricerw1=null, pricerw2=null, priceyw1=null, priceyw2=null, priceyw3=null, priceyd=null, priceed=null)
         * TrainDetail(trainno=Z385, type=Z, station=沈阳北, endstation=广州, departuretime=19:45, arrivaltime=05:04, sequenceno=3, costtime=1天9时19分, distance=0, isend=0, pricesw=null, pricetd=null, pricegr1=null, pricegr2=null, pricerw1=null, pricerw2=null, priceyw1=null, priceyw2=null, priceyw3=null, priceyd=null, priceed=null)
         */
    }

    @Test
    public void testExampleHandleResponse(){
        try {
            List<TrainDetail> trainDetails=HandleResponseUtil.exampleHandleResponse();
            for (TrainDetail trainDetail : trainDetails) {
                System.out.println(trainDetail);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tempTest(){
        System.out.println("1111"+null);
    }
}