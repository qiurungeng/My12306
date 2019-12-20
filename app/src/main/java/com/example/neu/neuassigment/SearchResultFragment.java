package com.example.neu.neuassigment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neu.neuassigment.adapter.TrainDetailAdapter;
import com.example.neu.neuassigment.gson.TrainDetail;
import com.example.neu.neuassigment.util.HandleRequestUtil;
import com.example.neu.neuassigment.util.HandleResponseUtil;

import java.util.List;

public class SearchResultFragment extends Fragment {
    private String start_station;
    private String end_station;
    private String isHigh;
    private String date;

    private List<TrainDetail> trainDetailList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchFragment searchFragment=(SearchFragment)getActivity()
                .getSupportFragmentManager()
                .findFragmentByTag("searchFragment");
        start_station=searchFragment.getStartStation();
        end_station=searchFragment.getEndStation();
        isHigh=searchFragment.isHigh();
        date=searchFragment.getDate();
        initTrainDetails();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_result,container,false);
        //填充界面
        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        TrainDetailAdapter adapter=new TrainDetailAdapter(trainDetailList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * 调用Util类中方法请求并得到所要查询的列车详情信息列表
     */
    private void initTrainDetails(){
//        String response= HandleRequestUtil.handleSearchTrainRequest("北京","上海","0","2020-01-01");
        String response= HandleRequestUtil.handleSearchTrainRequest(start_station,end_station,isHigh,date);
        trainDetailList= HandleResponseUtil.handleSearchTrainResponse(response);

    }
}
