package com.example.asus.mynotebook.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.NoteBean;
import com.example.asus.mynotebook.model.UserBean;
import com.example.asus.mynotebook.presenter.notepager.DetailsAdapter;
import com.example.asus.mynotebook.presenter.notepager.MynotesAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

public class NoteDetails extends AppCompatActivity {
    //笔记页面

    private RecyclerView rv_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        rv_detail = findViewById(R.id.rv_mynotes_detail);
        initRecycler();
    }

    private void initRecycler() {
        List<NoteBean> note = DataSupport.where("userId = ?", String.valueOf(Flags.currentAccount)).find(NoteBean.class);  //查不到
        rv_detail.setLayoutManager(new LinearLayoutManager(this));
        rv_detail.setAdapter(new DetailsAdapter(note, this));

    }
}
