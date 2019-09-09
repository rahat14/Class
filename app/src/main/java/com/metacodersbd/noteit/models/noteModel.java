package com.metacodersbd.noteit.models;

public class noteModel {

    String color_id , date , desc , id ,  title  ;



    public noteModel() {
    }

    public noteModel(String color_id, String date, String desc, String id, String title) {
        this.color_id = color_id;
        this.date = date;
        this.desc = desc;
        this.id = id;
        this.title = title;
    }
    // getter setter


    public String getColor_id() {
        return color_id;
    }

    public void setColor_id(String color_id) {
        this.color_id = color_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
