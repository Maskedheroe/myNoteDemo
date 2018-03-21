package com.example.asus.mynotebook.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.presenter.minepager.GlideImageLoader;
import com.example.asus.mynotebook.view.pages.minepager.MinePager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

public class UpdateIcon extends AppCompatActivity {
    private ImagePicker imagePicker;

    private ImageView imageView;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_icon);
        initIcon(this);
    }

    public   void initIcon(Activity context){
        initCamera();
    }

    private   void initCamera() {
        if (imagePicker == null){
            imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
            imagePicker.setShowCamera(true);  //显示拍照按钮
            imagePicker.setCrop(true);        //允许裁剪（单选才有效）
            imagePicker.setSaveRectangle(true); //是否按矩形区域保存
            imagePicker.setSelectLimit(9);    //选中数量限制
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
            imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
            imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
            imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
            imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
            Intent intent = new Intent(this, ImageGridActivity.class);
            this.startActivityForResult(intent,1 );
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 1) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                imagePath  = images.get(0).path;
                sendImagePath();
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendImagePath() {
        Intent intent = new Intent(this,MainActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString("icon", imagePath);//压入数据
        intent.putExtras(mBundle);
        startActivity(intent);

    }


}
