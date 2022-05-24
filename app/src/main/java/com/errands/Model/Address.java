package com.errands.Model;

public class Address {
    private String  id;
    private String User_id;
    private String address;

    public Address(String id, String User_id, String address) {
        this.id = id;
        User_id = User_id;
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
        return User_id;
    }

    public void setUser_id(String User_id) {
        User_id = User_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", User_id='" + User_id + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
