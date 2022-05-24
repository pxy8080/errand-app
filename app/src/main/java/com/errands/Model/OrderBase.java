package com.errands.Model;

public class OrderBase {
    private String id;
    private String User_id_send;
    private String User_id_receive;
    private String myAddress;
    private String taskAddress;
    private String type;
    private String name;
    private int state;

    public OrderBase(String id, String user_id_send, String user_id_receive, String myAddress, String taskAddress, String type, String name, int state) {
        this.id = id;
        this.User_id_send = user_id_send;
        this.User_id_receive = user_id_receive;
        this.myAddress = myAddress;
        this.taskAddress = taskAddress;
        this.type = type;
        this.name = name;
        this.state = state;
    }

    public OrderBase() {
    }

    @Override
    public String toString() {
        return "OrderBase{" +
                "id='" + id + '\'' +
                ", User_id_send='" + User_id_send + '\'' +
                ", User_id_receive='" + User_id_receive + '\'' +
                ", myAddress='" + myAddress + '\'' +
                ", taskAddress='" + taskAddress + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", state=" + state +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id_send() {
        return User_id_send;
    }

    public void setUser_id_send(String user_id_send) {
        User_id_send = user_id_send;
    }

    public String getUser_id_receive() {
        return User_id_receive;
    }

    public void setUser_id_receive(String user_id_receive) {
        User_id_receive = user_id_receive;
    }

    public String getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(String myAddress) {
        this.myAddress = myAddress;
    }

    public String getTaskAddress() {
        return taskAddress;
    }

    public void setTaskAddress(String taskAddress) {
        this.taskAddress = taskAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
