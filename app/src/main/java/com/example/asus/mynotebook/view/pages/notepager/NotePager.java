package com.example.asus.mynotebook.view.pages.notepager;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.presenter.DataOfmain;
import com.example.asus.mynotebook.presenter.GlideImageLoader;
import com.example.asus.mynotebook.presenter.notepager.MynotesAdapter;
import com.example.asus.mynotebook.view.activity.NoteDetails;
import com.example.asus.mynotebook.view.activity.WriteNoteActivity;
import com.example.asus.mynotebook.view.interfaces.BasePager;
import com.yalantis.phoenix.PullToRefreshView;
import com.youth.banner.Banner;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by asus on 2018/1/20.
 */

public class NotePager extends BasePager {

    private RecyclerView notes;
    private LinearLayout takeNotes;
    private PullToRefreshView refresh;
    private View myNotes;
    private View tip;

    public NotePager(Activity activity, FragmentManager mFragmentManager) {
        super(activity);
    }

    @Override
    public void initData() {
        View view = View.inflate(mactivity, R.layout.pager_note, null);
        notes = view.findViewById(R.id.rv_mynotes);
        myNotes = view.findViewById(R.id.ll_mynotes);
        takeNotes = view.findViewById(R.id.ll_takenotes);
        refresh = view.findViewById(R.id.pull_refresh);
//        tip = view.findViewById(R.id.tip);
        Banner banner = view.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(DataOfmain.getImgaes());
        banner.start();
        initRecycler(notes);
        frame_Content_Layout.addView(view);
        listener();
    }

    private void listener() {
        myNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flags.currentAccount!=-1){
                    mactivity.startActivity(new Intent(mactivity, NoteDetails.class));
                }else {
                    Toast.makeText(mactivity,"请先登陆!",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        takeNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flags.currentAccount == -1){
                    new SVProgressHUD(mactivity).showErrorWithStatus("未登录，请先登陆", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    return;
                }
                Intent intent = new Intent(mactivity,WriteNoteActivity.class);
                mactivity.startActivity(intent);
            }
        });
        refresh.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mactivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initRecycler(notes);
                            }
                        });
                        refresh.setRefreshing(false);
                    }
                },1000);
            }
        });
    }



    private void initRecycler(RecyclerView notes) {
        notes.setLayoutManager(new LinearLayoutManager(mactivity));
        if (Flags.currentAccount!=-1){
//            tip.setVisibility(View.INVISIBLE);
            List<CollectionBean> note = DataSupport.where("userId = ?", String.valueOf(Flags.currentAccount)).find(CollectionBean.class);
             notes.setAdapter(new MynotesAdapter(note,mactivity));
        }else {
//            tip.setVisibility(View.VISIBLE);
            notes.setVisibility(View.INVISIBLE);
        }
    }

}
