package com.example.neu.neuassigment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.neu.neuassigment.R;
import com.example.neu.neuassigment.gson.TrainDetail;

import java.util.List;

public class TrainDetailAdapter extends RecyclerView.Adapter<TrainDetailAdapter.ViewHolder> {

    private List<TrainDetail> myTrainDetailList;

    public TrainDetailAdapter(List<TrainDetail> trainDetailList) {
        myTrainDetailList=trainDetailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.train_detail_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrainDetail trainDetail=myTrainDetailList.get(position);
        holder.trainNO.setText(trainDetail.getTrainno());
        holder.startStation.setText(trainDetail.getStation());
        holder.endStation.setText(trainDetail.getEndstation());
        holder.startTime.setText(trainDetail.getDeparturetime());
        holder.endTime.setText(trainDetail.getArrivaltime());
        holder.timeCost.setText(trainDetail.getCosttime());
        holder.price_sw.setText(trainDetail.getPricesw());
        holder.price_yd.setText(trainDetail.getPriceyd());
        holder.price_ed.setText(trainDetail.getPriceed());
        holder.price_rw.setText(trainDetail.getPricerw());
        holder.price_yw.setText(trainDetail.getPriceyw());
        //硬座还不确定
    }

    @Override
    public int getItemCount() {
        return myTrainDetailList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView trainNO;
        TextView startStation;
        TextView endStation;
        TextView startTime;
        TextView endTime;
        TextView timeCost;
        TextView price_sw;
        TextView price_yd;
        TextView price_ed;
        TextView price_rw;
        TextView price_yw;
        TextView price_yz;

        public ViewHolder(View itemView) {
            super(itemView);
            trainNO=(TextView)itemView.findViewById(R.id.train_no);
            startStation=(TextView)itemView.findViewById(R.id.start_station);
            endStation=(TextView)itemView.findViewById(R.id.end_station);
            startTime=(TextView)itemView.findViewById(R.id.start_time);
            endTime=(TextView)itemView.findViewById(R.id.end_time);
            timeCost=(TextView)itemView.findViewById(R.id.time_cost);
            price_sw=(TextView)itemView.findViewById(R.id.price_sw);
            price_yd=(TextView)itemView.findViewById(R.id.price_yd);
            price_ed=(TextView)itemView.findViewById(R.id.price_ed);
            price_rw=(TextView)itemView.findViewById(R.id.price_rw);
            price_yw=(TextView)itemView.findViewById(R.id.price_yw);
            price_yz=(TextView)itemView.findViewById(R.id.price_yz);
        }
    }
}
