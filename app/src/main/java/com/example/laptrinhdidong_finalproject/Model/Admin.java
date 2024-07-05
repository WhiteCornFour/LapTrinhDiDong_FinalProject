package com.example.laptrinhdidong_finalproject.Model;

public class Admin {
    String adminID;
    String adminName;
    String loginAccount;
    String loginPassword;

    public Admin() {
    }

    public Admin(String adminID, String adminName, String loginAccount, String loginPassword) {
        this.adminID = adminID;
        this.adminName = adminName;
        this.loginAccount = loginAccount;
        this.loginPassword = loginPassword;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
