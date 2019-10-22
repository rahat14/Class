package com.metacodersbd.noteit;

public class profileModel {

    String name  , ppLink  ;

    public profileModel() {

    }

    public profileModel(String name, String ppLink) {
        this.name = name;
        this.ppLink = ppLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPpLink() {
        return ppLink;
    }

    public void setPpLink(String ppLink) {
        this.ppLink = ppLink;
    }
}
