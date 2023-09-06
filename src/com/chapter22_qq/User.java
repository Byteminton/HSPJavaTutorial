package com.chapter22_qq;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L; //增强兼容性，不写也可以
    private String userID;
    private String password;

    public User() {
    }

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
