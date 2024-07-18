package com.example.laptrinhdidong_finalproject.Model;

public class Orders {
    private String orderID;
    private String cartID;
    private String orderDate;
    private String shipAddress;
    private double orderTotal;

    // Getters and Setters
    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) { this.orderID = orderID; }
    public String getCartID() {
        return cartID;
    }
    public void setCartID(String cartID) {
        this.cartID = cartID;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getShipAddress() {
        return shipAddress;
    }
    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }
    public double getOrderTotal() {
        return orderTotal;
    }
    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Orders(String orderID, String cartID, String orderDate, String shipAddress, double orderTotal) {
        this.orderID = orderID;
        this.cartID = cartID;
        this.orderDate = orderDate;
        this.shipAddress = shipAddress;
        this.orderTotal = orderTotal;
    }
    public Orders(String cartID, String orderDate, String shipAddress, double orderTotal) {
        this.cartID = cartID;
        this.orderDate = orderDate;
        this.shipAddress = shipAddress;
        this.orderTotal = orderTotal;
    }
    public Orders() {
    }
}
