package com.example.neu.neuassigment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.neu.neuassigment.db.User;

public class ProfileFragment extends Fragment {

    private User loginUser;
    private EditText username;
    private EditText userType;
    private EditText name;
    private EditText idCardType;
    private EditText idCardNumber;
    private EditText phoneNumber;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        username=(EditText)view.findViewById(R.id.profile_username);
        userType=(EditText)view.findViewById(R.id.profile_user_type);
        name=(EditText)view.findViewById(R.id.profile_name);
        idCardType=(EditText)view.findViewById(R.id.profile_IDCard_type);
        idCardNumber=(EditText)view.findViewById(R.id.profile_IDCard_number);
        phoneNumber=(EditText)view.findViewById(R.id.profile_phone_number);
        button=(Button)view.findViewById(R.id.profile_btn);
        username.setFocusable(false);

        loginUser=((MainActivity)getActivity()).getUser();
        username.setText(loginUser.getUsername());
        userType.setText(loginUser.getUserType());
        name.setText(loginUser.getName());
        idCardType.setText(loginUser.getIdCardType());
        idCardNumber.setText(loginUser.getIdCardNumber());
        phoneNumber.setText(loginUser.getPhoneNumber());

        //可被编辑的个人信息
        final EditText[] editTexts=new EditText[]{name,userType,idCardNumber,idCardType,phoneNumber};
        setEditTextEditable(editTexts,false);
        button.setOnClickListener(new View.OnClickListener() {
            boolean inEditing=false;
            @Override
            public void onClick(View v) {
                if (!inEditing){
                    inEditing=true;
                    button.setText("保存");
                    setEditTextEditable(editTexts,true);
                }else {
                    loginUser.setName(name.getText().toString());
                    loginUser.setIdCardNumber(idCardNumber.getText().toString());
                    loginUser.setIdCardType(idCardType.getText().toString());
                    loginUser.setPhoneNumber(phoneNumber.getText().toString());
                    loginUser.setUserType(userType.getText().toString());
                    loginUser.updateAll("username = ?",loginUser.getUsername());
                    inEditing=false;
                    button.setText("编辑");
                    setEditTextEditable(editTexts,false);
                }
            }
        });
        return view;
    }

    //变更EditText的可编辑状态
    private void setEditTextEditable(EditText[] editTexts,boolean editable){
        for (EditText editText : editTexts) {
            editText.setFocusableInTouchMode(editable);
            editText.setFocusable(editable);
        }
    }
}
