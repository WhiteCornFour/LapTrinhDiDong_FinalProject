package com.example.laptrinhdidong_finalproject.Model;

public class ProductCategory {
     String categoryID;
     String categoryName;
     String categoryDescription;
     byte[] CategoryImage;

    public ProductCategory(String categoryID, String categoryName, String categoryDescription, byte[] categoryImage) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.CategoryImage = categoryImage;
    }

    public ProductCategory() {
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

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public byte[] getCategoryImage() {
        return CategoryImage;
    }

    public void setCategoryImage(byte[] categoryImage) {
        CategoryImage = categoryImage;
    }
}
