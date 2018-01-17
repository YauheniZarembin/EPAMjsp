package com.zarembin.epampjsp.entity;

public class AuthenticationInfo {
    private String userName;
    private String password;
    private boolean isBan;
    private boolean isAdmin;

    public AuthenticationInfo(String userName, String password, boolean isBan, boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.isBan = isBan;
        this.isAdmin = isAdmin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBan() {
        return isBan;
    }

    public void setBan(boolean ban) {
        isBan = ban;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
