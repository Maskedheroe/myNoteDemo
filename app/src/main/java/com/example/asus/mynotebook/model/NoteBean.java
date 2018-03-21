package com.example.asus.mynotebook.model;



import android.graphics.Bitmap;

import org.litepal.crud.DataSupport;

import java.util.Calendar;

/**
 * Created by asus on 2018/1/22.
 */

public class NoteBean extends DataSupport {
    private Calendar mdate;
    private int id;
    private String title;
    private String date;
    private String course;
    private String content;
    private UserBean user;
    private int userId;

    public NoteBean() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public NoteBean(String title, String content, String course, int currentAccount) {
        this.title = title;
        this.course = course;
        this.content = content;
        mdate = Calendar.getInstance();
        this.date =  mdate.get(Calendar.MONTH)+mdate.get(Calendar.DATE)+mdate.get(Calendar.HOUR_OF_DAY)+""; ;
        userId = currentAccount;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {this.date = date;}

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
