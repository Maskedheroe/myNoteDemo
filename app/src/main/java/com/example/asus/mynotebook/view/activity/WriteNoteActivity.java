package com.example.asus.mynotebook.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bumptech.glide.Glide;
import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.NoteBean;
import com.example.asus.mynotebook.model.UserBean;
import com.example.asus.mynotebook.presenter.notepager.AddPhotos;
import com.example.asus.mynotebook.presenter.notepager.GridAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;

public class WriteNoteActivity extends AppCompatActivity implements View.OnClickListener {

    //书写页面
    private EditText writetitle;
    private EditText writetext;
    private ImageButton deleteContent;
    private ImageButton addPhotos;
    private ImageButton commit;
    private MaterialSpinner spinner;
    private ArrayList<Object> searchSpinnerList;
    private String currentCourse;
    private ImageView iv_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        initView();
    }

    private void initView() {
        writetitle = findViewById(R.id.tv_write_title);
        writetext = findViewById(R.id.et_writetext);
        addPhotos = findViewById(R.id.ib_addphotos);
        deleteContent = findViewById(R.id.ib_deletetext);
        commit = findViewById(R.id.ib_commit);
        spinner = findViewById(R.id.write_note_spinner);
        iv_note = findViewById(R.id.iv_note);
        setListener();
    }

    private void setListener() {
        writetext.setOnClickListener(this);
        writetitle.setOnClickListener(this);
        addPhotos.setOnClickListener(this);
        deleteContent.setOnClickListener(this);
        commit.setOnClickListener(this);
        initSpinner();
    }

    private void initSpinner() {
        searchSpinnerList = new ArrayList<>();
        searchSpinnerList.add("数学");
        searchSpinnerList.add("语文");
        searchSpinnerList.add("英语");
        searchSpinnerList.add("物理");
        searchSpinnerList.add("化学");
        searchSpinnerList.add("生物");
        searchSpinnerList.add("历史");
        searchSpinnerList.add("地理");
        searchSpinnerList.add("政治");
        spinner.setItems("无", "数学", "语文", "英语", "物理", "化学", "生物", "历史", "地理", "政治");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                currentCourse = searchSpinnerList.get(position - 1).toString();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_write_title:
                break;
            case R.id.et_writetext:
                break;
            case R.id.ib_addphotos:
                Intent intent = new Intent(this, UpdateIcon.class);
                startActivity(intent);
                Bundle extras = this.getIntent().getExtras();
                if (extras != null && !writetitle.getText().toString().equals("")) {  //选择图片后也要检验是否有标题
                    String icon = extras.getString("icon");
                    Glide.with(this).load(Uri.fromFile(new File(icon))).into(iv_note);
                    NoteBean noteBean = new NoteBean(writetitle.getText().toString(), writetext.getText().toString(), currentCourse, Flags.currentAccount, icon); //存储方法
                    if (noteBean.save()) {
                        new SVProgressHUD(this).showSuccessWithStatus("保存成功", SVProgressHUD.SVProgressHUDMaskType.Clear);
                        //执行数据存储逻辑
                    } else {
                        new SVProgressHUD(this).showErrorWithStatus("保存失败", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    }
                } else {
                    showNotitle();
                }
                break;
            case R.id.ib_deletetext:
                if (!writetext.getText().toString().isEmpty()) {
                    writetext.getText().clear();//清空文本
                    new SVProgressHUD(this).showInfoWithStatus("已清空", SVProgressHUD.SVProgressHUDMaskType.Clear);
                } else {
                    new SVProgressHUD(this).showInfoWithStatus("内容已为空", SVProgressHUD.SVProgressHUDMaskType.Clear);
                }
                break;
            case R.id.ib_commit:
                if (writetitle.getText().toString().equals("")) {
                    showNotitle();
                } else if (!DataSupport.where("title = ?", writetitle.getText().toString()).find(NoteBean.class).isEmpty()) { //此处是判断错题本重名的逻
                    if (DataSupport.where("title = ?", writetitle.getText().toString()).find(NoteBean.class).get(0).getUser() != null) {
                        if (!DataSupport.where("title = ?", writetitle.getText().toString()).find(NoteBean.class).isEmpty() &&
                                DataSupport.where("title = ?", writetitle.getText().toString()).find(NoteBean.class).get(0).getUser().getId() == Flags.currentAccount)
                            new SVProgressHUD(this).showInfoWithStatus("已存在该收藏", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    }
                } else if (currentCourse == null) {
                    new SVProgressHUD(this).showErrorWithStatus("没有选择课程", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    return;
                } else {
                    NoteBean noteBean = new NoteBean(writetitle.getText().toString(), writetext.getText().toString(), currentCourse, Flags.currentAccount); //存储方法
                    if (noteBean.save()) {
                        new SVProgressHUD(this).showSuccessWithStatus("保存成功", SVProgressHUD.SVProgressHUDMaskType.Clear);
                        //执行数据存储逻辑
                    } else {
                        new SVProgressHUD(this).showErrorWithStatus("保存失败", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    }

                }
                break;

        }
    }

    public void showNotitle() {
        new SVProgressHUD(this).showInfoWithStatus("没有标题", SVProgressHUD.SVProgressHUDMaskType.Clear);
    }
}

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//            if (data != null && requestCode == IMAGE_PICKER) {
//                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//
//                grid_note.setAdapter(adapter);
//            } else {
//                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
