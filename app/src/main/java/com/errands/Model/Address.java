package com.errands.Model;

public class Address {
    private String  id;
    private String user_id;
    private String address;

    public Address(String id, String user_id, String address) {
        this.id = id;
        this.user_id = user_id;
        this.address = address;
    }

    public Address() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
