package com.errands.Model;

public class Bill {
    private String id;
    private String user_id;
    private String orderbase_id;
    private String date;
    private String time;
    private double income;
    private double payment;

    public Bill() {
    }

    public Bill(String id, String user_id, String orderbase_id, String date, String time, double income, double payment) {
        this.id = id;
        this.user_id = user_id;
        this.orderbase_id = orderbase_id;
        this.date = date;
        this.time = time;
        this.income = income;
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", orderbase_id='" + orderbase_id + '\'' +
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrderbase_id() {
        return orderbase_id;
    }

    public void setOrderbase_id(String orderbase_id) {
        this.orderbase_id = orderbase_id;
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

