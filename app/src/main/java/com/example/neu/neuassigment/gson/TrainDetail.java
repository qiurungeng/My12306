package com.example.neu.neuassigment.gson;

import lombok.Data;

@Data
public class TrainDetail {
    //列车号(Z14)
    String trainno;
    //类型:G高铁，Z直达，T特快，K快车，D动车
    String type;
    //出发
    String station;
    //终点
    String endstation;
    //出发时间
    String departuretime;
    //到达时间
    String arrivaltime;
    //
    String sequenceno;
    //时长
    String costtime;
    //距离
    String distance;
    //
    String isend;
    //商务座
    String pricesw;
    //
    String pricetd;
    //高级软卧
    String pricegr1;
    //高级软卧
    String pricegr2;
    //软卧
    String pricerw1;
    //软卧
    String pricerw2;
    //硬卧
    String priceyw1;
    //硬卧
    String priceyw2;
    //硬卧
    String priceyw3;
    //一等座
    String priceyd;
    //二等座
    String priceed;

    public String getPricerw(){
        if (pricerw1!=null)return pricerw1;
        else if (pricerw2!=null)return pricerw2;
        else return "无";
    }

    public String getPriceyw(){
        if (priceyw1!=null)return priceyw1;
        else if (priceyw2!=null)return priceyw2;
        else if (priceyw3!=null)return priceyw3;
        else return "无";
    }

}
