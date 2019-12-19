package com.example.neu.neuassigment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Toolbar extends AppCompatActivity {

    private DrawerLayout myDrawerLayout;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //显示toolbar.xml菜单文件
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    //处理 toolbar menu 的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                myDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.toolbar_train:
                Toast.makeText(this,"查看列车时刻表",Toast.LENGTH_SHORT).show();

                break;
            case R.id.toolbar_profile:
                Toast.makeText(this,"进入个人信息页",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Toolbar.this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.settings:
                Toast.makeText(this,"SETTINGS",Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
        return true;
    }
}
