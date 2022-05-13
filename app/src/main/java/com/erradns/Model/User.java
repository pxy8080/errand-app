package com.erradns.Model;

/**
 * id 账号id
 * phone 用户电话
 * email 用户邮箱
 * password 用户密码
 * nickname 用户昵称
 * headportait 用户头像
 * school 用户学校
 */

public class User {
    private String id;
    private String phone;
    private String email;
    private String password;
    private String nickname;
    private String headportrait;
    private String school;


    @Override
    public String toString() {
        return "User{" +
                "  id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email +'\''+
                ", password='" + password +'\''+
                ", nickname='" + nickname + '\'' +
                ", headportrait='" + headportrait +'\''+
                ", school='" + school + '\'' +
                '}';
    }

    public User(String id, String phone, String email, String password, String nickname, String headportrait, String school) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.headportrait = headportrait;
        this.school = school;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadportrait() {
        return headportrait;
    }

    public void setHeadportrait(String headportrait) {
        this.headportrait = headportrait;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}

