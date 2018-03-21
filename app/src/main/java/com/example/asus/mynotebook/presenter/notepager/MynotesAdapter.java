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
 * Created by asus on 2018/1/22.
 */

public class MynotesAdapter extends RecyclerView.Adapter<MynotesAdapter.ViewHolder> {

    private final Context context;
    public ArrayList<CollectionBean> noteLists;
    private boolean isDeleteAble = true;

    public MynotesAdapter(List list, Context context){
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
        View inflate = View.inflate(parent.getContext(), R.layout.mynotes_adapter_item, null);
        ViewHolder viewHolder = new ViewHolder(inflate);

        viewHolder.content.setOnLongClickListener(new NoteOnLongClickListener(context,inflate,this,viewHolder));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CollectionBean noteBean  = noteLists.get(position);
        Glide.with(context).load(noteBean.getContentMap()).into(holder.imageContent);//Glide用加载二进制流来加载图片
        holder.course.setText(noteBean.getCourse());
        holder.date.setText(noteBean.getDate());
        holder.title.setText(noteBean.getTitle());

    }

    @Override
    public int getItemCount() {
        return noteLists.size();
    }

}
