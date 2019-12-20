package com.example.neu.neuassigment.util;

import com.example.neu.neuassigment.gson.TrainDetail;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HandleResponseUtil {

    /**
     * 从请求API后得到的Json字符串中提取列车班次详情信息列表。
     * @param response
     * @return
     */
    public static List<TrainDetail> handleSearchTrainResponse(String response){
        JsonParser parser=new JsonParser();
        JsonElement element=parser.parse(response);
        if (element instanceof JsonObject){
            List<TrainDetail> trainDetailArrayList=new ArrayList<>();
            JsonObject jsonObject=element.getAsJsonObject();
            JsonObject result=jsonObject.getAsJsonObject("result");
            JsonArray trainDetails=result.getAsJsonArray("list");

            for (JsonElement jsonElement : trainDetails) {
                TrainDetail trainDetail=new Gson().fromJson(jsonElement.toString(),TrainDetail.class);
                trainDetailArrayList.add(trainDetail);
            }

            return trainDetailArrayList;
        }else {
            return null;
        }
    }

    public static List<TrainDetail> exampleHandleResponse() throws IOException {
        BufferedReader bufferedReader=new BufferedReader(new FileReader("C:\\Users\\Apollos\\Desktop\\example.json"));
        String response=bufferedReader.readLine();
        return handleSearchTrainResponse(response);
    }


}
