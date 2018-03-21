package com.example.asus.mynotebook.view.interfaces;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.asus.mynotebook.R;

/**
 * Created by asus on 2017/11/8.
 */

public abstract class BasePager implements IBasePage{

    public FrameLayout frame_Content_Layout;

    public   Activity mactivity;
    public   View mRootview;

    public BasePager(Activity activity){
        mactivity = activity;
        mRootview = initView();
    }

    @Override
    public View initView() {
        View view = View.inflate(mactivity, R.layout.base_pagers_of_main, null);
        frame_Content_Layout = view.findViewById(R.id.fl_content);
        return view;
    }
}
