package com.example.neu.neuassigment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFragment extends Fragment {

    private MainActivity mainActivity;
    private EditText startStation;
    private EditText endStation;
    private TextView dateTV;
    private Button searchBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity=(MainActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search,container,false);
        //查询界面控件
        startStation=(EditText)view.findViewById(R.id.start_station);
        endStation=(EditText)view.findViewById(R.id.end_station);
        dateTV =(TextView)view.findViewById(R.id.date);
        searchBtn=(Button)view.findViewById(R.id.search_btn);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateClick(v);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainActivity,startStation.getText().toString()+","+
                        endStation.getText().toString()+","+dateTV.getText().toString(),Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(MainActivity.this,TrainDetailActivity.class);
//                startActivity(intent);
                mainActivity.replaceFragment(new SearchResultFragment());
            }
        });
    }


    /**查询界面**/

    public void setDateValue(int year,int month,int day){
        dateTV.setText(year+"年"+(month+1+"月"+day));
    }

    //设置日期的按钮事件
    public void setDateClick(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(mainActivity.getSupportFragmentManager(), "datePicker");

    }

}
