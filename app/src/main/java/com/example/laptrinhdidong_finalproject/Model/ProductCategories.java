package com.example.laptrinhdidong_finalproject.Model;

import android.graphics.Bitmap;

public class ProductCategories {
    Bitmap categoryImage;
    String categoryID;
    String categoryName;

    public Bitmap getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(Bitmap categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ProductCategories(String categoryID, String categoryName, Bitmap categoryImage) {
        this.categoryImage = categoryImage;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public ProductCategories() {
    }
}