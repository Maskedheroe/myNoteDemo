package com.example.asus.mynotebook.model;

import android.graphics.Bitmap;
import android.provider.ContactsContract;

import org.litepal.crud.DataSupport;

import java.util.Calendar;

/**
 * Created by asus on 2018/2/27.
 */

public class CollectionBean extends DataSupport {
    private Calendar mdate;
    private int id;
    private String title;
    private String date;
    private final String course;
    private final int userId;
    private final String contentMap;

    public Calendar getMdate() {
        return mdate;
    }

    public void setMdate(Calendar mdate) {
        this.mdate = mdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCourse() {
        return course;
    }





    public int getUserId() {
        return userId;
    }


    public String getContentMap() {
        return contentMap;
    }


    public CollectionBean(String title, String course, String contentMap, int id) {
            this.title = title;
            this.course = course;
            this.contentMap = contentMap;
            this.userId = id;
    }


}
