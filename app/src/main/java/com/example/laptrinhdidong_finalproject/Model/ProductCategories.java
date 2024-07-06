package com.example.laptrinhdidong_finalproject.Model;

import java.io.Serializable;
import java.sql.Blob;

public class ProductCategories implements Serializable {
    String idCategory;
    String nameCategory;
//    String descriptionCategory;
    private byte[] imageCategory;

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

//    public String getDescriptionCategory() {
//        return descriptionCategory;
//    }
//
//    public void setDescriptionCategory(String descriptionCategory) {
//        this.descriptionCategory = descriptionCategory;
//    }

    public byte[] getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(byte[] imageCategory) {
        this.imageCategory = imageCategory;
    }

    public ProductCategories(String idCategory, String nameCategory, byte[] imageCategory) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
//        this.descriptionCategory = descriptionCategory;
        this.imageCategory = imageCategory;
    }

    public ProductCategories() {
    }
}

