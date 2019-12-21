package com.example.neu.neuassigment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.neu.neuassigment.db.User;

public class ProfileFragment extends Fragment {

    private User loginUser;
    private EditText username;
//    private EditText userType;
//    private EditText idCardType;
    private EditText name;
    private EditText idCardNumber;
    private EditText phoneNumber;
    private Spinner userType;
    private Spinner idCardType;
    private Button button;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        username=(EditText)view.findViewById(R.id.profile_username);
        userType=(Spinner) view.findViewById(R.id.spinner_user_type);
        name=(EditText)view.findViewById(R.id.profile_name);
        idCardType=(Spinner) view.findViewById(R.id.spinner_idCard_type);
        idCardNumber=(EditText)view.findViewById(R.id.profile_IDCard_number);
        phoneNumber=(EditText)view.findViewById(R.id.profile_phone_number);
        button=(Button)view.findViewById(R.id.profile_btn);
        username.setFocusable(false);

        loginUser=((MainActivity)getActivity()).getUser();
        //用户名
        username.setText(loginUser.getUsername());
        //用户类型
        String[] utype=new String[]{"成人","军人","学生"};
        ArrayAdapter<String> userTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, utype);
        userType.setAdapter(userTypeAdapter);
        userType.setSelection(getItemIndexInArray(utype,loginUser.getUserType()));
        //ID卡类型
        String[] idctype=new String[]{"身份证","学生证","军人证"};
        ArrayAdapter<String> idCardTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, idctype);
        idCardType.setAdapter(idCardTypeAdapter);
        idCardType.setSelection(getItemIndexInArray(idctype,loginUser.getIdCardType()));
        //姓名
        name.setText(loginUser.getName());
        //ID号
        idCardNumber.setText(loginUser.getIdCardNumber());
        //电话
        phoneNumber.setText(loginUser.getPhoneNumber());

        //可被编辑的个人信息
        setEditable(false);

        button.setOnClickListener(new View.OnClickListener() {
            boolean inEditing=false;
            @Override
            public void onClick(View v) {
                if (!inEditing){
                    inEditing=true;
                    button.setText("保存");
                    setEditable(true);
                }else {
                    loginUser.setName(name.getText().toString());
                    loginUser.setIdCardNumber(idCardNumber.getText().toString());
                    loginUser.setIdCardType(idCardType.getSelectedItem().toString());
                    loginUser.setPhoneNumber(phoneNumber.getText().toString());
                    loginUser.setUserType(userType.getSelectedItem().toString());
                    loginUser.updateAll("username = ?",loginUser.getUsername());
                    inEditing=false;
                    button.setText("编辑");
                    setEditable(false);
                }
            }
        });
        return view;
    }

    //变更EditText的可编辑状态
    private void setEditable(boolean editable){
        EditText[] editTexts=new EditText[]{name,idCardNumber,phoneNumber};
        idCardType.setEnabled(editable);
        userType.setEnabled(editable);
        for (EditText editText : editTexts) {
            editText.setFocusableInTouchMode(editable);
            editText.setFocusable(editable);
        }
    }

    private int getItemIndexInArray(String[] array,String item){
        for (int i=0;i<array.length;i++){
            if (item.equals(array[i]))return i;
        }
        return -1;
    }
}
