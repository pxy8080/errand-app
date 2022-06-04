package com.errands.Model;


import java.util.List;

public class Orderinfo {
    OrderBase orderBase;
    List<Order> orderList;

    public Orderinfo() {
    }

    public Orderinfo(OrderBase orderBase, List<Order> orderList) {
        this.orderBase = orderBase;
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "Oderinfo{" +
                "orderBase=" + orderBase +
                ", orderList=" + orderList +
                '}';
    }

    public OrderBase getOrderBase() {
        return orderBase;
    }

    public void setOrderBase(OrderBase orderBase) {
        this.orderBase = orderBase;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
