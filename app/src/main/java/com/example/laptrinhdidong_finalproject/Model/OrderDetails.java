package com.example.laptrinhdidong_finalproject.Model;

public class OrderDetails {
    private String orderID;
    private String productID;
    private String cartID;
    private String size;
    private int quantity;
    private double unitPrice;

    // Constructor
    public OrderDetails(String orderID, String productID, String cartID, String size, int quantity, double unitPrice) {
        this.orderID = orderID;
        this.productID = productID;
        this.cartID = cartID;
        this.size = size;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderDetails() {
    }

    // Getters and Setters
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
