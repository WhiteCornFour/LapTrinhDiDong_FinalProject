package com.example.laptrinhdidong_finalproject.Model;

public class Carts {
    String cartID;
    String customerID;

    public Carts(String cartID, String customerID) {
        this.cartID = cartID;
        this.customerID = customerID;
    }
    public Carts(String customerId) {
        this.customerID = customerId;
        this.cartID = generateCartId(customerId);
    }
    public Carts() {
    }
    private String generateCartId(String customerId) {
        return "Cart" + customerId;
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
