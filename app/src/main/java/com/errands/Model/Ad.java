package com.errands.Model;


public class Ad {
    String target;
    String picture;

    public Ad(String target, String picture) {
        this.target = target;
        this.picture = picture;
    }

    public Ad() {
    }

    @Override
    public String toString() {
        return "Ad{" +
                "target='" + target + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}