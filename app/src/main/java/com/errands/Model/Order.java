package com.errands.Model;

public class Order {
    private String orderId;
    private String name;
    private String description;
    private String evidence;
    private int amount;
    private double estimation;
    private String picture;

    public Order(String orderId, String name, String description, String evidence, int amount, double estimation, String picture) {
        this.orderId = orderId;
        this.name = name;
        this.description = description;
        this.evidence = evidence;
        this.amount = amount;
        this.estimation = estimation;
        this.picture = picture;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", evidence='" + evidence + '\'' +
                ", amount=" + amount +
                ", estimation=" + estimation +
                ", picture='" + picture + '\'' +
                '}';
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getEstimation() {
        return estimation;
    }

    public void setEstimation(double estimation) {
        this.estimation = estimation;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
