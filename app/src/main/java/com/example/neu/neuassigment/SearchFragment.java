package com.example.neu.neuassigment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neu.neuassigment.db.Station;
import com.example.neu.neuassigment.util.AssetsFileReadUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private MainActivity mainActivity;
    private TextView startStation;
    private TextView endStation;
    private TextView dateTV;
    private RadioGroup isHighRG;
    private Button searchTrainInfoBtn;
    private SharedPreferences myPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity=(MainActivity)getActivity();
        myPref = PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search,container,false);
        //查询界面控件
        startStation=(TextView)view.findViewById(R.id.start_station);
        endStation=(TextView)view.findViewById(R.id.end_station);
        dateTV =(TextView)view.findViewById(R.id.date);
        isHighRG=(RadioGroup)view.findViewById(R.id.rg_isHigh);
        searchTrainInfoBtn =(Button)view.findViewById(R.id.search_btn);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        startStation.setText(myPref.getString("startStation","北京"));
        endStation.setText(myPref.getString("endStation","上海"));
        dateTV.setText(myPref.getString("date","点击选择日期"));

        //站点TextView监听：点击车站TextView弹出对话框
        startStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSearchDialog("startStation",startStation);
            }
        });
        endStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSearchDialog("endStation",endStation);
            }
        });

        //日期选择点击事件监听
        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateClick(v);
            }
        });

        //最终查找列车时刻详情表按钮
        searchTrainInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainActivity,getStartStation()+","+
                        getEndStation()+","+getDate()+
                        ","+isHigh(),Toast.LENGTH_SHORT).show();
                mainActivity.replaceFragment(new SearchResultFragment(),"searchResultFragment");
            }
        });

    }


    /**选择日期**/

    public void setDateValue(int year,int month,int day){
        month+=1;
        String month_string;
        String day_string;
        if (month<10)month_string="0"+month;
        else month_string=""+month;
        if (day<10)day_string="0"+day;
        else day_string=""+day;
        String dateString=year+"-"+month_string+"-"+day_string;
        dateTV.setText(dateString);
        saveStringLocally("date",dateString);
    }

    //设置日期的按钮事件
    public void setDateClick(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(mainActivity.getSupportFragmentManager(), "datePicker");

    }

    /**车站检索**/

    //弹出并加载车站搜索对话框
    private void popSearchDialog(final String sharedPreferencesKey,final TextView station){
        //点击站点TextView弹出对话框
        final Dialog dialog = new Dialog(getContext());
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.dialog_search, null);
        dialog.setContentView(dialogView);

        //找到搜索框与结果列表
        SearchView searchStationsSV=(SearchView)dialogView.findViewById(R.id.station_search);
        ListView searchStationResultLV=(ListView)dialogView.findViewById(R.id.station_search_result);

        //车站搜索界面适配
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item,getAllStations());
        searchStationResultLV.setAdapter(adapter);
        searchStationResultLV.setTextFilterEnabled(true);

        //车站搜索界面监听
        searchStationsSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    //使用此方法搜索会出现一个黑色框框
                    //mListView.setFilterText(newText);
                    //未显示效果采用下面方法 不会出现黑色框
                    adapter.getFilter().filter(newText);
                }else{
                    //mListView.setFilterText(newText);
                    adapter.getFilter().filter("");
                }
                return false;
            }
        });

        //车站查询结果项点击事件监听
        searchStationResultLV.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)adapter.getItem(position);
                station.setText(item);
                saveStringLocally(sharedPreferencesKey,item);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**获取站点名数组**/
    private String[] getAllStations(){
        ArrayList<String> stationNames=new ArrayList<>();
        List<Station> stations=DataSupport.findAll(Station.class);
        if (stations.size()==0){
            //数据库中未存储车站信息
            stations = AssetsFileReadUtil.getAllStations(getContext());
        }
        for (Station station : stations) {
            stationNames.add(station.getName());
        }
        String[] returnArray=stationNames.toArray(new String[stationNames.size()]);
        for (String s : returnArray) {
            System.out.print(s+",");
        }
        System.out.println();
        return returnArray;
    }

    /**SharePref保存String到本地**/
    private void saveStringLocally(String key,String value){
        SharedPreferences.Editor editor=myPref.edit();
        editor.putString(key,value);
        System.out.println("已保存————————————————————————");
        editor.apply();
    }

    /**协助其他Activity获取TextView的值**/
    protected String getStartStation(){ return startStation.getText().toString(); }
    protected String getEndStation(){ return endStation.getText().toString(); }
    protected String getDate(){ return dateTV.getText().toString(); }
    protected String isHigh(){
        RadioButton rb=(RadioButton) getView().findViewById(isHighRG.getCheckedRadioButtonId());
        String isHigh=rb.getText().toString();
        return isHigh.equals("高铁")?"1":"0";
    }

}
