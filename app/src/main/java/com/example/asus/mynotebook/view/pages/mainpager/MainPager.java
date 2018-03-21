package com.example.asus.mynotebook.view.pages.mainpager;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.presenter.mainpager.BlankFragment;
import com.example.asus.mynotebook.presenter.mainpager.MainViewPagerAdapter;
import com.example.asus.mynotebook.view.interfaces.BasePager;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by asus on 2018/1/20.
 */

public class MainPager extends BasePager {
    //主页面

    private final FragmentManager fragmentManager;
    private int[] imageArray;


    private ArrayList<BasePager> viewPagers;
    private TabLayout tablayout;
    private ViewPager vp_main;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private MainViewPagerAdapter viewPagerAdapter;

    public MainPager(Activity activity, FragmentManager mFragmentManager) {
        super(activity);
        fragmentManager = mFragmentManager;
    }



    @Override
    public void initData() {
        View view = View.inflate(mactivity, R.layout.pager_main, null);
        frame_Content_Layout.addView(view);
        tablayout = view.findViewById(R.id.tabLayout);
        vp_main = view.findViewById(R.id.vp_main);
        initTablayout();
        if (Flags.CURRENT_STATUS!=0)//初始化添加科目
        initAddCourse();
    }

    private void initAddCourse() {
       /* ImageView icon = new ImageView(mactivity);
        icon.setImageDrawable(mactivity.getResources().getDrawable(R.drawable.additem,null));
        FloatingActionButton actionButton = new FloatingActionButton.Builder(mactivity)
                .setContentView(icon)
                .build();
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(mactivity);
        SubActionButton button1 = itemBuilder.setContentView(icon).build();
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(mactivity)
                .addSubActionView(button1)
                .attachTo(actionButton)
                .build();*/

        ImageView icon = new ImageView(mactivity); // Create an icon
        icon.setImageDrawable(mactivity.getResources().getDrawable(R.drawable.additem,null));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(mactivity)
                .setContentView(icon)
                .setPosition(3)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(mactivity);
// repeat many times:
        ImageView itemIcon = new ImageView(mactivity);
        itemIcon.setImageDrawable(mactivity.getResources().getDrawable(R.drawable.addcourse,null));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(mactivity)
                .addSubActionView(button1)
                // ...
                .attachTo(actionButton)
                .build();
    }

    private void initTablayout() {

        //tablayout的配置

        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        if (fragmentList.size()==0){
            fragmentList.add(new BlankFragment("数学"));
            fragmentList.add(new BlankFragment("语文"));
            fragmentList.add(new BlankFragment("英语"));
            fragmentList.add(new BlankFragment("物理"));
            fragmentList.add(new BlankFragment("化学"));
            fragmentList.add(new BlankFragment("生物"));
            fragmentList.add(new BlankFragment("历史"));
            fragmentList.add(new BlankFragment("地理"));
            fragmentList.add(new BlankFragment("政治"));
        }
        titleList.add("数学");
        titleList.add("语文");
        titleList.add("英语");
        titleList.add("物理");
        titleList.add("化学");
        titleList.add("生物");
        titleList.add("历史");
        titleList.add("地理");
        titleList.add("政治");

        tablayout.addTab(tablayout.newTab().setText("数学"));
        tablayout.addTab(tablayout.newTab().setText("语文"));
        tablayout.addTab(tablayout.newTab().setText("英语"));
        tablayout.addTab(tablayout.newTab().setText("物理"));
        tablayout.addTab(tablayout.newTab().setText("化学"));
        tablayout.addTab(tablayout.newTab().setText("生物"));
        tablayout.addTab(tablayout.newTab().setText("历史"));
        tablayout.addTab(tablayout.newTab().setText("地理"));
        tablayout.addTab(tablayout.newTab().setText("政治"));

        if (viewPagerAdapter == null){
            viewPagerAdapter = new MainViewPagerAdapter(fragmentManager,fragmentList,titleList);

            vp_main.setAdapter(viewPagerAdapter);
        }else {
            viewPagerAdapter.notifyDataSetChanged();
        }


        tablayout.setupWithViewPager(vp_main);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getText().toString()){
                    case "数学":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.math,null));
                        break;
                    case "语文":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.chinese,null));
                        break;
                    case "英语":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.english,null));
                        break;
                    case "物理":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.physical,null));
                        break;
                    case "化学":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.chemical,null));
                        break;
                    case "生物":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.biology,null));
                        break;
                    case "历史":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.history,null));
                        break;
                    case "地理":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.geography,null));
                        break;
                    case "政治":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.politics,null));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getText().toString()){
                    case "数学":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.math,null));
                        break;
                    case "语文":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.chinese,null));
                        break;
                    case "英语":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.english,null));
                        break;
                    case "物理":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.physical,null));
                        break;
                    case "化学":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.chemical,null));
                        break;
                    case "生物":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.biology,null));
                        break;
                    case "历史":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.history,null));
                        break;
                    case "地理":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.geography,null));
                        break;
                    case "政治":
                        tablayout.setBackground(mactivity.getResources().getDrawable(R.drawable.politics,null));
                        break;
                }
            }
        });
    }



}
