package com.example.asus.mynotebook.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.view.interfaces.ContentFragement;

public class MainActivity extends AppCompatActivity {
    //主活动 管理主页面 连接ContentFragment

    private static final String TAG_CONTENT = "TAG_CONTENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onShow();
        //    hideToFullScreen();
        initFragment();
    }
    /* 【1】 初始化fragrment*/
    private void initFragment(){
        FragmentManager fm = getSupportFragmentManager();//一般通过Manager拿到FM ！！！
        FragmentTransaction transaction = fm.beginTransaction();//开启事务
        transaction.replace(R.id.fm_main,new ContentFragement(fm) , TAG_CONTENT);
        //开启侧边栏与内容碎片！！！  注意要调的是Main的layout
        //而且需要继承自V4包的Fragrment!
        transaction.commit(); //提交事务!!

    }


    public void onShow() {

        //※※※※※※解决沉浸式的方法！！


        //5.0以上手动设置statusBar为透明。不能使用getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //因为这在4.4,5.0确实是全透明，但是在6.0是半透明!!!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/
    }
}
