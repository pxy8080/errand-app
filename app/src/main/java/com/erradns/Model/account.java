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

public class account {
    private String id;
    private int phone;
    private String email;
    private String password;
    private String nickname;
    private String headportrait;
    private String school;

    private Boolean islogin;

    @Override
    public String toString() {
        return "account{" +
                "  id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email +'\''+
                ", nickname='" + nickname + '\'' +
                ", headportrait='" + headportrait +'\''+
                ", school='" + school + '\'' +
                ", islogin='" + islogin + '\'' +
                '}';
    }


    public account() {
    }

    public account(String id, int phone, String email,String password, String nickname, String headportrait, String school,  Boolean islogin) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.nickname = nickname;
        this.headportrait = headportrait;
        this.school = school;
        this.password = password;
        this.islogin = islogin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIslogin() {
        return islogin;
    }

    public void setIslogin(Boolean islogin) {
        this.islogin = islogin;
    }
}

