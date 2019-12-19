package com.example.neu.neuassigment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout myDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //DrawerLayout
        myDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.nav_list);
        }

        //NavigationView
        NavigationView navView=(NavigationView)findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_train_time);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                myDrawerLayout.closeDrawers();
                return true;
            }
        });

        replaceFragment(new SearchFragment());
    }


    /**界面整体**/

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
                replaceFragment(new SearchFragment());
                break;
            case R.id.toolbar_profile:
                Toast.makeText(this,"进入个人信息页",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.settings:
                Toast.makeText(this,"SETTINGS",Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
        return true;
    }


    //替换Fragment
    protected void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.search_ticket,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
//

}
