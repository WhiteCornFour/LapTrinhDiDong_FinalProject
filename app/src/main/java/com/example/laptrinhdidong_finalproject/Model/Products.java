package com.example.laptrinhdidong_finalproject.Model;

import java.io.Serializable;

public class Products implements Serializable {
    String idProduct;
    String idCategory;
    String nameProduct;
    String descriptionProduct;
    Float initialPrice;
    private byte[] imageProduct;

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public Float getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Float initialPrice) {
        this.initialPrice = initialPrice;
    }

    public byte[] getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(byte[] imageProduct) {
        this.imageProduct = imageProduct;
    }

    public Products(String idProduct, String idCategory, String nameProduct, String descriptionProduct, Float initialPrice, byte[] imageProduct) {
        this.idProduct = idProduct;
        this.idCategory = idCategory;
        this.nameProduct = nameProduct;
        this.descriptionProduct = descriptionProduct;
        this.initialPrice = initialPrice;
        this.imageProduct = imageProduct;
    }

    public Products() {
    }
}
