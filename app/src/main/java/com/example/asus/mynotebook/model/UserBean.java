package com.example.asus.mynotebook.model;

import android.renderscript.Sampler;

import com.example.asus.mynotebook.flags.Flags;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2018/2/3.
 */

public class UserBean extends DataSupport {
    private int id;
    private List<NoteBean>  noteId = new ArrayList<>();
    private List<CollectionBean> collectionBeans = new ArrayList<>();

    public List<CollectionBean> getCollectionBeans() {
        return collectionBeans;
    }

    public void setCollectionBeans(List<CollectionBean> collectionBeans) {
        this.collectionBeans = collectionBeans;
    }

    private String userName;
    private String userPwd;

    public UserBean(String userName, String userPwd) {
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public List<NoteBean> getNoteId() {
        return DataSupport.where("userId = ?", String.valueOf(Flags.currentAccount)).find(NoteBean.class);
    }

    public void setNoteId(List<NoteBean> noteId) {
        this.noteId = noteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
