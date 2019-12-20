package com.example.neu.neuassigment.util;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HandleRequestUtil {

    //向极速数据火车票查询API发送请求时时要用到的appkey，可在极速数据网注册账号获得
    private static String appkey="574743780fae33cd";

    /**
     * 使用极速数据火车站站查询API请求列车班次及时刻表
     * @param from_station 出发站
     * @param to_station 目的站
     * @param isHigh 是否为高铁
     * @param date 日期
     * @return
     */
    public static String handleSearchTrainRequest
            (String from_station,String to_station,String isHigh,String date){
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder =HttpUrl.parse("https://api.jisuapi.com/train/station2s")
                .newBuilder();
        urlBuilder.addQueryParameter("appkey",appkey);
        urlBuilder.addQueryParameter("start", from_station);
        urlBuilder.addQueryParameter("end", to_station);
        urlBuilder.addQueryParameter("date", date);
        urlBuilder.addQueryParameter("ishigh",isHigh);
        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();

        String response=new HandleRequestUtil().sendRequestWithOkHttp(request);
        return response;
    }

    private String sendRequestWithOkHttp(final Request request){
        //创建发送请求的新线程
        RequestThread requestThread=new RequestThread();
        requestThread.setRequest(request);
        //执行该线程
        ExecutorService exec=Executors.newFixedThreadPool(1);
        Future<String> future=exec.submit(requestThread);
        //得到请求结果
        String response=null;
        try {
            response=future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 用来发送OkHttp.Request的线程
     */
    private class RequestThread implements Callable<String> {

        private Request request;

        public void setRequest(Request request){
            this.request=request;
        }

        @Override
        public String call() throws Exception {
            OkHttpClient client=new OkHttpClient();
            String responseString=null;
            try {
                Response response = client.newCall(request).execute();
                responseString=response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }
    }

}
