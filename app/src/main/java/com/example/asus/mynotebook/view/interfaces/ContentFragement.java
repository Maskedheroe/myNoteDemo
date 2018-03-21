package com.example.asus.mynotebook.view.interfaces;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.utils.NoScrollViewPager;
import com.example.asus.mynotebook.view.interfaces.BaseFragrment;
import com.example.asus.mynotebook.view.interfaces.BasePager;
import com.example.asus.mynotebook.view.pages.findpager.FindPager;
import com.example.asus.mynotebook.view.pages.mainpager.MainPager;
import com.example.asus.mynotebook.view.pages.minepager.MinePager;
import com.example.asus.mynotebook.view.pages.notepager.NotePager;

import java.util.ArrayList;

/**
 * Created by asus on 2018/1/20.
 */

@SuppressLint("ValidFragment")
public class ContentFragement extends BaseFragrment {

    private RadioGroup group;
    private NoScrollViewPager content;
    private ArrayList<BasePager> mPagers;
    private final FragmentManager mFragmentManager;

    public ContentFragement(FragmentManager fm) {
        mFragmentManager = fm;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_main_content, null);
        group = view.findViewById(R.id.rg_group);
        content = view.findViewById(R.id.vp_content);
        initData();
        return view;
    }

    @Override
    public void initData() {
        mPagers = new ArrayList<>();
        mPagers.add(new MainPager(mActivity,mFragmentManager));
        mPagers.add(new FindPager(mActivity,mFragmentManager));
        mPagers.add(new NotePager(mActivity,mFragmentManager));
        mPagers.add(new MinePager(mActivity,mFragmentManager));

        content.setAdapter(new ContentAdapter()); //ViewPager需要设置适配器

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(RadioGroup group, int checkedId) {
                     switch (checkedId){
                         case R.id.rb_main:
                             content.setCurrentItem(0,false);
                             break;
                         case R.id.rb_find:
                             content.setCurrentItem(1,false);
                             break;
                         case R.id.rb_note:
                             content.setCurrentItem(2,false);
                             break;
                         case R.id.rb_mine:
                             content.setCurrentItem(3,false);
                             break;
                         default:
                             break;
                     }
                 }
         });

        content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                BasePager pager = mPagers.get(position);
                pager.initData(); //每次切换过来都要进行初始化数据
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 手动加载第一页数据
        mPagers.get(0).initData();
    }
    //ViewPager的使用需要适配器
    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override //初始化布局
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagers.get(position);
            View view = pager.mRootview;  //获得当前页面对象的布局
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
