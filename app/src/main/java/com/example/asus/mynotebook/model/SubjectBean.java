package com.example.asus.mynotebook.model;

import org.litepal.crud.DataSupport;

/**
 * Created by asus on 2018/2/3.
 */

public class SubjectBean extends DataSupport {
    private int id;
    private String subTitle;
    private String subContent;
    private String subClass;
    private String subSys;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public String getSubSys() {
        return subSys;
    }

    public void setSubSys(String subSys) {
        this.subSys = subSys;
    }


}
