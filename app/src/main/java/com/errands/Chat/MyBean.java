package com.errands.Chat;

public class MyBean {
    private String data;
    private String time,name;
    private int number;

    public MyBean() {
    }

    public MyBean(String data, String time, String name, int number) {
        this.data = data;
        this.time = time;
        this.name = name;
        this.number = number;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
