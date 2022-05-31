package com.errands.Model;


public class DetailOrder extends OrderBase {
    private String orderId;
    private String description;
    private String evidence;
    private int amount;
    private double estimation;
    private String picture;


    public DetailOrder(String id, String user_id_send, String user_id_receive, String myAddress, String taskAddress, String type, String name, int state, String date, String time, String orderId, String description, String evidence, int amount, double estimation, String picture) {
        super(id, user_id_send, user_id_receive, myAddress, taskAddress, type, name, state, date, time);
        this.orderId = orderId;
        this.description = description;
        this.evidence = evidence;
        this.amount = amount;
        this.estimation = estimation;
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public DetailOrder() {

    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    @Override
    public String toString() {
        return "DetailOrder{" +
                "orderId='" + orderId + '\'' +
                ", description='" + description + '\'' +
                ", evidence='" + evidence + '\'' +
                ", amount=" + amount +
                ", estimation=" + estimation +
                ", picture='" + picture + '\'' +
                '}';
    }
}
