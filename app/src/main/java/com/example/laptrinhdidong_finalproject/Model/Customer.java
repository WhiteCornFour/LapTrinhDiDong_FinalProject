package com.example.laptrinhdidong_finalproject.Model;

public class Customer {
    String idCustomer;
    String nameCustomer;
    String emailCustomer;
    String phoneCustomer;
    String accountCustomer;
    String passwordCustomer;

    public Customer(String idCustomer, String nameCustomer, String emailCustomer, String phoneCustomer, String accountCustomer, String passwordCustomer) {
        this.idCustomer = idCustomer;
        this.nameCustomer = nameCustomer;
        this.emailCustomer = emailCustomer;
        this.phoneCustomer = phoneCustomer;
        this.accountCustomer = accountCustomer;
        this.passwordCustomer = passwordCustomer;
    }

    public Customer() {
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public void setPhoneCustomer(String phoneCustomer) {
        this.phoneCustomer = phoneCustomer;
    }

    public String getAccountCustomer() {
        return accountCustomer;
    }

    public void setAccountCustomer(String accountCustomer) {
        this.accountCustomer = accountCustomer;
    }

    public String getPasswordCustomer() {
        return passwordCustomer;
    }

    public void setPasswordCustomer(String passwordCustomer) {
        this.passwordCustomer = passwordCustomer;
    }
}
