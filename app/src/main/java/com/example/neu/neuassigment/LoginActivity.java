package com.example.neu.neuassigment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neu.neuassigment.bean.User;
import com.example.neu.neuassigment.gson.ResultDTO;
import com.example.neu.neuassigment.service.UserService;

public class LoginActivity extends AppCompatActivity {
    private User user;
    private UserService userService=new UserService();
    private SharedPreferences myPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn=(Button) findViewById(R.id.login_button);
        final EditText usernameET=(EditText) findViewById(R.id.username);
        final EditText passwordET=(EditText) findViewById(R.id.password);
        TextView registerTV=(TextView) findViewById(R.id.jump_to_register);
        myPref =PreferenceManager.getDefaultSharedPreferences(this);

        usernameET.setText(myPref.getString("local_username",""));
        passwordET.setText(myPref.getString("local_password",""));

        //登录按钮监听事件
//        loginBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                String username = usernameET.getText().toString();
//                String password = passwordET.getText().toString();
//                int loginResult=verify(username,password);
//                if (loginResult==1){
//                    Toast.makeText(LoginActivity.this,"登陆成功！",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                    intent.putExtra("loginUser",user);
//                    startActivity(intent);
//                    finish();
//                }else if (loginResult==2){
//                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(LoginActivity.this,"尚未注册，请先注册",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                ResultDTO loginResult=userService.login(username,password);
                Toast.makeText(LoginActivity.this,loginResult.getMsg(),Toast.LENGTH_SHORT).show();
                if (loginResult.getState()==1){ //登录成功
                    //记住账号密码
                    SharedPreferences.Editor editor=myPref.edit();
                    editor.putString("local_username",username);
                    editor.putString("local_password",password);
                    editor.apply();
                    //跳转到主界面
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("loginUser",loginResult.getUser());
                    startActivity(intent);
                    finish();
                }
            }
        });

        //注册按钮监听事件
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

//    private int verify(String username,String password){
//        User loginUser= DataSupport.where("username = ?",username).findFirst(User.class);
//        if(loginUser!=null){
//            if (loginUser.getPassword().equals(password)){
//                SharedPreferences.Editor editor=myPref.edit();
//                editor.putString("local_username",username);
//                editor.putString("local_password",password);
//                editor.apply();
//                user=loginUser;
//                return 1;   //登陆成功
//            }else return 2; //密码错误
//        }else {
//            return 0;       //尚未注册
//        }
//    }
}
