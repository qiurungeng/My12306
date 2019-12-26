package com.example.neu.neuassigment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neu.neuassigment.bean.User;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout myDrawerLayout;
    private TextView nav_username;
    private TextView nav_name;
    private User loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginUser=(User) getIntent().getSerializableExtra("loginUser");
        System.out.println(loginUser);

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
                switch (item.getTitle().toString()){
                    case "个人信息设置":
                        switchToProfilePage();
                        break;
                    case "列车时刻查询":
                        switchToSearchPage();
                        break;
                    case "查看我的订单":
                        Toast.makeText(MainActivity.this,"暂不支持此功能",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                myDrawerLayout.closeDrawers();
                return true;
            }
        });
        View nav_header=navView.getHeaderView(0);
        nav_name=(TextView)nav_header.findViewById(R.id.nav_name);
        nav_username=(TextView)nav_header.findViewById(R.id.nav_username);
        nav_name.setText(loginUser.getName());
        nav_username.setText(loginUser.getUsername());

        //填充布局
        replaceFragment(new SearchFragment(),"searchFragment");
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
                //菜单栏：点击列车时刻表查询按钮
                switchToSearchPage();
                break;
            case R.id.toolbar_profile:
                //菜单栏：点击个人信息编辑按钮
                switchToProfilePage();
                break;
            case R.id.settings:
                Toast.makeText(this,"SETTINGS",Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
        return true;
    }

    //跳转到列车时刻查询页面
    private void switchToSearchPage(){
        Toast.makeText(this,"查看列车时刻表",Toast.LENGTH_SHORT).show();
        Fragment searchFragment=getSupportFragmentManager().findFragmentByTag("searchFragment");
        if (searchFragment==null){
            replaceFragment(new SearchFragment(),"searchFragment");
        }else replaceFragment(searchFragment);
    }

    //跳转到个人信息设置页面
    private void switchToProfilePage(){
        //菜单栏：点击个人信息编辑按钮
        Toast.makeText(this,"进入个人信息页",Toast.LENGTH_SHORT).show();
        Fragment profileFragment=getSupportFragmentManager().findFragmentByTag("profileFragment");
        if (profileFragment==null){
            replaceFragment(new ProfileFragment(),"profileFragment");
        }else replaceFragment(profileFragment);
    }


    //替换Fragment
    protected void replaceFragment(Fragment fragment,String tag){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.search_ticket,fragment,tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void replaceFragment(Fragment fragment){
        replaceFragment(fragment,fragment.getTag());
    }


    protected User getUser() {
        return loginUser;
    }


    /**重写返回键,退出前弹窗提示**/
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getFragments().size()<=1&&
                getSupportFragmentManager().findFragmentById(R.id.search_ticket) instanceof SearchFragment){
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("确定要退出应用么？")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                            System.exit(0);
                        }
                    }).show();
            dialog.setCanceledOnTouchOutside(false);
        }else {
            super.onBackPressed();
        }
    }
}
