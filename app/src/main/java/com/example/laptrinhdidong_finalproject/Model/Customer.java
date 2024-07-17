package com.example.laptrinhdidong_finalproject.Model;

import java.io.Serializable;

public class Customer implements Serializable {
    String idCustomer;
    String nameCustomer;
    String emailCustomer;
    String phoneCustomer;
    String passwordCustomer;
    byte[] customerImage;

    public byte[] getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(byte[] customerImage) {
        this.customerImage = customerImage;
    }

    public Customer() {
    }

    public Customer(String idCustomer, String nameCustomer, String emailCustomer, String phoneCustomer,
                    String passwordCustomer, byte[] customerImage) {
        this.idCustomer = idCustomer;
        this.nameCustomer = nameCustomer;
        this.emailCustomer = emailCustomer;
        this.phoneCustomer = phoneCustomer;
        this.passwordCustomer = passwordCustomer;
        this.customerImage = customerImage;
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

    public String getPasswordCustomer() {
        return passwordCustomer;
    }

    public void setPasswordCustomer(String passwordCustomer) {
        this.passwordCustomer = passwordCustomer;
    }
}
