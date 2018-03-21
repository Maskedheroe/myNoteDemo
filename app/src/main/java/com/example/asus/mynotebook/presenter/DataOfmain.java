package com.example.asus.mynotebook.presenter;

import com.example.asus.mynotebook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2018/1/21.
 */

public class DataOfmain {
    public static int []int_images  = {R.drawable.scroll_home1, R.drawable.scroll_home2,R.drawable.scroll_home3,R.drawable.scroll_home4};

    public static List<Integer> getImgaes (){
        List<Integer> imageView = new ArrayList<>();
        for (int i = 0; i < int_images.length; i++) {
            imageView.add(int_images[i]);
        }
        return imageView;
    }
}
