package com.example.asus.mynotebook.presenter.notepager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.model.NoteBean;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by asus on 2018/2/28.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private final Context context;
    public ArrayList<NoteBean> noteLists;
    private boolean isDeleteAble = true;
    private View inflate;

    public DetailsAdapter (List list, Context context){
        noteLists = (ArrayList) list;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView course;
        private final TextView title;
        private final TextView date;
        private final TextView content;
        private final ImageView imageContent;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_note_title);
            course = itemView.findViewById(R.id.tv_note_course);
            date = itemView.findViewById(R.id.tv_note_date);
            content = itemView.findViewById(R.id.tv_content);
            imageContent = itemView.findViewById(R.id.iv_content);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        inflate = View.inflate(parent.getContext(), R.layout.mynotes_adapter_item, null);
        final ViewHolder viewHolder = new ViewHolder(inflate);
        viewHolder.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("操作");
                final String[] items = {"分组", "详情","删除"};
                builder.setNegativeButton("取消", null);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (items[which]) {
                            //业务逻辑
                            case "删除":
                                deleteAnim(viewHolder);
                                DataSupport.deleteAll(NoteBean.class,"title = ?" , viewHolder.title.getText().toString());
                                break;
                            default:
                                //        Toast.makeText(mcontext,"功能未开放",Toast.LENGTH_SHORT).show();
                                //如何刷新呢?
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
         return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoteBean noteBean  = noteLists.get(position);
        holder.content.setText(noteBean.getContent());
        holder.course.setText(noteBean.getCourse());
        holder.date.setText(noteBean.getDate());
        holder.title.setText(noteBean.getTitle());
    }


    @Override
    public int getItemCount() {
        return noteLists.size();
    }

    private void deleteAnim(ViewHolder viewHolder) {
        change(viewHolder);
    }
    private void change(ViewHolder viewHolder) {
        if (isDeleteAble) {//此时为增加动画效果，刷新部分数据源，防止删除错乱
            ExplosionField.attach2Window((Activity) context).explode(inflate);  //要用attach2Window
            this.notifyItemRemoved(viewHolder.getAdapterPosition());
            isDeleteAble = false;//初始值为true,当点击删除按钮以后，休息0.5秒钟再让他为
            //true,起到让数据源刷新完成的作用
            int position = viewHolder.getAdapterPosition()+ this.noteLists.size();  //定位   ※※※完美解决！！※※※※※※为什么要加  ？？？？
            this.noteLists.remove(position);//删除数据源
            this.notifyItemRemoved(position);//刷新被删除的地方
            this.notifyItemRangeChanged(position, this.getItemCount()); //刷新被删除数据，以及其后面的数据

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
