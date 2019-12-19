package com.example.neu.neuassigment;

import org.junit.Test;

import java.io.IOException;

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
        urlBuilder.addQueryParameter("appkey","****");
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();

    }
}