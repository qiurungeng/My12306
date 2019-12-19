package com.example.neu.neuassigment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn=(Button) findViewById(R.id.login_button);
        final EditText usernameET=(EditText) findViewById(R.id.username);
        final EditText passwordET=(EditText) findViewById(R.id.password);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                if (verify(username,password)){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean verify(String username,String password){
        if(username.equals("root")&&password.equals("asdasdasd")){
            return true;
        }else {
            return false;
        }
    }
}
