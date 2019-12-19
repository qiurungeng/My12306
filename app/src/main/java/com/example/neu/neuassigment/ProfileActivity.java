package com.example.neu.neuassigment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        EditText username=(EditText)findViewById(R.id.profile_username);
        username.setFocusable(false);
        EditText userType=(EditText)findViewById(R.id.profile_user_type);
        EditText name=(EditText)findViewById(R.id.profile_name);
        EditText idCardType=(EditText)findViewById(R.id.profile_IDCard_type);
        EditText idCardNumber=(EditText)findViewById(R.id.profile_IDCard_number);
        EditText phoneNumber=(EditText)findViewById(R.id.profile_phone_number);
        final Button button=(Button)findViewById(R.id.profile_btn);

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
                    inEditing=false;
                    button.setText("编辑");
                    setEditTextEditable(editTexts,false);
                }
            }
        });
    }

    //变更EditText的可编辑状态
    private void setEditTextEditable(EditText[] editTexts,boolean editable){
        for (EditText editText : editTexts) {
            editText.setFocusableInTouchMode(editable);
            editText.setFocusable(editable);
        }
    }


}
