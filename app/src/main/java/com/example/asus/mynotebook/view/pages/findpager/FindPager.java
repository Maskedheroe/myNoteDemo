package com.example.asus.mynotebook.view.pages.findpager;

import android.app.Activity;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.model.NoteBean;
import com.example.asus.mynotebook.presenter.findpager.FindRecyclerAdapter;
import com.example.asus.mynotebook.view.interfaces.BasePager;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;

/**
 * Created by asus on 2018/1/20.
 */

public class FindPager extends BasePager {
    //笔记页面

    private MaterialSpinner sp_search;
    private SearchView search;
    private ArrayList<String> searchSpinnerList;
    private ArrayAdapter<String> searchAdapter;
    private boolean isAutoSelect;
    private RecyclerView recycler;

    public FindPager(Activity activity, FragmentManager mFragmentManager) {
        super(activity);
        Log.d("FindPage","init Constructor" );
    }

    @Override
    public void initData() {
        Log.d("FindPage","init Methord" );
        View view = View.inflate(mactivity, R.layout.pager_find, null);
        frame_Content_Layout.addView(view);
        sp_search = view.findViewById(R.id.sp_search);
        search = view.findViewById(R.id.search);
        recycler = view.findViewById(R.id.rv_find);
        initSpinner();
    }

    private void initSpinner() {
        initRecycler();
        searchSpinnerList = new ArrayList<>();
        searchSpinnerList.add("题库");
        searchSpinnerList.add("错题");
        sp_search.setItems("题库","错题");
        search.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                if (Flags.currentAccount!=-1){
                    if (Flags.SEARCH_STATE == Flags.SEARCH_ONNOTE) {
                        List<NoteBean> noteBeans = DataSupport.where("title = ?",string).find(NoteBean.class);
                        recycler.setAdapter(new FindRecyclerAdapter(noteBeans, mactivity, Flags.SEARCH_ONNOTE));//为什么此处集合不能使用泛型??
                    }else {
                        List<CollectionBean> noteBeans = DataSupport.where("title = ?",string).find(CollectionBean.class);
                        recycler.setAdapter(new FindRecyclerAdapter(noteBeans, mactivity, Flags.SEARCH_ONNOTE));
                    }
                }else{
                    new SVProgressHUD(mactivity).showErrorWithStatus("未登录，请先登陆", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    return;
                }
            }
        });
        sp_search.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (searchSpinnerList.get(position)=="题库") {  //根据选择进行标记用户的状态
                    Flags.SEARCH_STATE = Flags.SEARCH_ONLINE;
                }else {
                    Flags.SEARCH_STATE = Flags.SEARCH_ONNOTE;
                }
            }
        });

    }

    private void initRecycler() {  //初始化Recycler
        recycler.setLayoutManager(new LinearLayoutManager(mactivity));
    }
}
