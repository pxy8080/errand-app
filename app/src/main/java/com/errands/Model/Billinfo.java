package com.errands.Model;

public class Billinfo {
    private String id;
    private String userName;
    private String type;
    private String date;
    private String time;
    private double income;
    private double payment;

    public Billinfo() {
    }

    public Billinfo(String id, String userName, String type, String date, String time, double income, double payment) {
        this.id = id;
        this.userName = userName;
        this.type = type;
        this.date = date;
        this.time = time;
        this.income = income;
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Billinfo{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", income=" + income +
                ", payment=" + payment +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
}
