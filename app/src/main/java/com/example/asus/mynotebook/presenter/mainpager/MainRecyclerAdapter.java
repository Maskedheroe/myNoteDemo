package com.example.asus.mynotebook.presenter.mainpager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.model.NoteBean;

import com.example.asus.mynotebook.presenter.notepager.NoteOnLongClickListener;
import com.example.asus.mynotebook.utils.DrawableToBytes;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by asus on 2018/2/26.
 */

public class MainRecyclerAdapter  extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>{
    private final Context context;
    public ArrayList<CollectionBean> noteLists;
    private boolean isDeleteAble = true;

    public MainRecyclerAdapter(Context context, ArrayList<CollectionBean> noteLists) {
        this.context = context;
        this.noteLists = noteLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View inflate = View.inflate(parent.getContext(), R.layout.main_notepager_recycleritem, null);
        final ViewHolder viewHolder = new ViewHolder(inflate);

        viewHolder.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (Flags.currentAccount != 1){
                    Toast.makeText(parent.getContext(),"只有管理身份才可操作！",Toast.LENGTH_SHORT).show();
                    return true;
                }else {
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
                                    deleteAnim(inflate,viewHolder);
                                    DataSupport.deleteAll(CollectionBean.class,"title = ?" , viewHolder.title.getText().toString());
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
                return true;
            }
        });

        viewHolder.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据库添加逻辑
                CollectionBean collectionBean = new CollectionBean(viewHolder.title.getText().toString(),
                        viewHolder.course.getText().toString(),
                        viewHolder.url.getText().toString() ,
                        Flags.currentAccount);
                if (collectionBean.save()){
                    if (Flags.USER!=null){
                        Flags.USER.getCollectionBeans().add(collectionBean);
                        Flags.USER.saveOrUpdate("id = ?",Flags.USER.getId()+"");
                        Toast.makeText(parent.getContext(),"收藏成功！",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(parent.getContext(),"收藏失败！请先登陆！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(parent.getContext(),"收藏失败！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CollectionBean noteBean  = noteLists.get(position);
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.orange);
        Glide.with(context)
                .load(noteBean.getContentMap())
                .apply(requestOptions)
                .into( holder.content);  //Glide用加载二进制流来加载图片
        holder.course.setText(noteBean.getCourse());
        holder.date.setText(noteBean.getDate());
        holder.title.setText(noteBean.getTitle());
        holder.url.setText(noteBean.getContentMap());
    }

    @Override
    public int getItemCount() {
        return noteLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView course;
        private final TextView title;
        private final TextView date;
        private final ImageView content;
        private final ImageButton collection;
        private final TextView url;

        public ViewHolder(View itemView) {
            super(itemView);
            url = itemView.findViewById(R.id.url);
            title = itemView.findViewById(R.id.tv_notepager_title);
            course = itemView.findViewById(R.id.tv_notepager_course);
            date = itemView.findViewById(R.id.tv_notepager_date);
            content = itemView.findViewById(R.id.tv_notepager_content);
            collection = itemView.findViewById(R.id.ib_collection);
        }
    }

    private void deleteAnim(View inflate, ViewHolder viewHolder) {
        change(inflate,viewHolder);
    }
    private void change(View inflate, ViewHolder viewHolder) {
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
