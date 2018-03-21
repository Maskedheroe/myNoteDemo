package com.example.asus.mynotebook.presenter.notepager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.model.NoteBean;

import org.litepal.crud.DataSupport;

import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by asus on 2018/2/8.
 */

public class NoteOnLongClickListener implements View.OnLongClickListener {
    private boolean isDeleteAble = true;
    private Context mcontext;
    private View mRootItemView;
    private MynotesAdapter mAdapter;
    private MynotesAdapter.ViewHolder viewHolder;

    public NoteOnLongClickListener(Context mcontext, View mRootItemView, MynotesAdapter mAdapter, MynotesAdapter.ViewHolder viewHolder) {
        this.mcontext = mcontext;
        this.mRootItemView = mRootItemView;
        this.mAdapter = mAdapter;
        this.viewHolder = viewHolder;
    }

    @Override
    public boolean onLongClick(View v) {
        TextView tv_title = mRootItemView.findViewById(R.id.tv_note_title);
        String str_title = tv_title.getText().toString();
        showDialog(str_title);
        return true;
    }
    private void showDialog(final String str_title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle("操作");
        final String[] items = {"分组", "详情","删除"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (items[which]) {
                    //业务逻辑
                    case "删除":
                        deleteAnim();
                        DataSupport.deleteAll(CollectionBean.class,"title = ?" , str_title);
                        break;
                    default:
                        //        Toast.makeText(mcontext,"功能未开放",Toast.LENGTH_SHORT).show();
                        //如何刷新呢?
                        break;
                }
            }
        });
        builder.show();
    }
    private void deleteAnim() {
        change();
    }
    private void change() {
        if (isDeleteAble) {//此时为增加动画效果，刷新部分数据源，防止删除错乱
            ExplosionField.attach2Window((Activity) mcontext).explode(mRootItemView);  //要用attach2Window
            mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            isDeleteAble = false;//初始值为true,当点击删除按钮以后，休息0.5秒钟再让他为
            //true,起到让数据源刷新完成的作用
            int position = viewHolder.getAdapterPosition()+ mAdapter.noteLists.size();  //定位   ※※※完美解决！！※※※※※※为什么要加  ？？？？
            mAdapter.noteLists.remove(position);//删除数据源
            mAdapter.notifyItemRemoved(position);//刷新被删除的地方
            mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount()); //刷新被删除数据，以及其后面的数据

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);//休息
                        isDeleteAble = true;//可点击按钮
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
