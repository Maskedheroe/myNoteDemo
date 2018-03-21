package com.example.asus.mynotebook.presenter.mainpager;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by asus on 2018/2/10.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    //解决再次加载失效的方法 FragmentStatePagerAdapter  但是光标会与Fragment不同步.
    private final List<Fragment> fragmentList;
    private final List<String> str_title;



    public MainViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> titleList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
        this.str_title = titleList;
    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return str_title.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
       return FragmentStatePagerAdapter.POSITION_NONE;
    }
}
