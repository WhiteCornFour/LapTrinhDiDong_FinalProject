package com.example.laptrinhdidong_finalproject.Model;

public class Carts {
    String cartID;
    String customerID;

    public Carts(String cartID, String customerID) {
        this.cartID = cartID;
        this.customerID = customerID;
    }

    public Carts() {
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
