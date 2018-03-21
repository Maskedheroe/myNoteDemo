package com.example.asus.mynotebook.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by asus on 2018/3/21.
 */

public class GlideImageLoader {
    public static void glideLoaderByByte(Context context, Byte[] bytes, ImageView view){
        Glide.with(context).load(bytes).into(view);
    }
    public static void glideLoaderByURL(Context context, String URL, ImageView view){
        Glide.with(context).load(URL).into(view);
    }
}
