package com.example.laptrinhdidong_finalproject.Model;

import android.graphics.Bitmap;

public class Product {
    private String productID;
    private String categoryID;
    private String productName;
    private String productDescription;
    private Bitmap productImage;
    private Double initialPrice;

    public Product() {
    }

    public Product(String productID, String categoryID, String productName, String productDescription, Bitmap productImage, Double initialPrice) {
        this.productID = productID;
        this.categoryID = categoryID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.initialPrice = initialPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Bitmap getProductImage() {
        return productImage;
    }

    public void setProductImage(Bitmap productImage) {
        this.productImage = productImage;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }
}
