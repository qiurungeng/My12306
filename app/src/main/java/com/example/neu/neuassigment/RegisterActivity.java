package com.example.neu.neuassigment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neu.neuassigment.db.User;

import org.litepal.crud.DataSupport;

public class RegisterActivity extends AppCompatActivity {
    String username;
    String password;
    String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText usernameET=(EditText)findViewById(R.id.register_username);
        final EditText passwordET=(EditText)findViewById(R.id.register_password);
        final EditText confirmPasswordET=(EditText)findViewById(R.id.register_confirm_password);
        Button registerBtn=(Button)findViewById(R.id.register_button);
        TextView jumpToLogin=(TextView)findViewById(R.id.jump_to_Login);

        //跳转回登录界面
        jumpToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        //注册用户
        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String username=usernameET.getText().toString();
                String password=passwordET.getText().toString();
                String confirmPassword=confirmPasswordET.getText().toString();

                if (isBlank(username)||isBlank(password)||isBlank(confirmPassword)){
                    System.out.println("username:"+username);
                    System.out.println("password:"+password);
                    System.out.println("cpassword:"+confirmPassword);
                    Toast.makeText(RegisterActivity.this,"请将将注册信息填写完整！",Toast.LENGTH_SHORT).show();
                }else if (!password.equals(confirmPassword)){
                    Toast.makeText(RegisterActivity.this,"密码与确认密码填写不一致，请重试",Toast.LENGTH_SHORT).show();
                }else {
                    User user=new User();
                    user.setUsername(username);
                    user.setPassword(password);

                    User check= DataSupport.where("username = ? ",username).findFirst(User.class);
                    System.out.println(check);
                    if (check!=null){
                        Toast.makeText(RegisterActivity.this,"该用户名已被注册，换一个试试？",Toast.LENGTH_SHORT).show();
                    }else {
                        boolean save = user.save();
                        if (save){
                            Toast.makeText(RegisterActivity.this,"注册成功！返回登录界面进行登录吧~",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                // 判断字符是否为空格、制表符、tab
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }
}
