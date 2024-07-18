package com.example.laptrinhdidong_finalproject.Model;

import java.io.Serializable;

public class Revenue implements Serializable {
    String revenueID;
    int year;
    int month;
    double totalRevenue;

    public Revenue() {
    }

    public Revenue(String revenueID, int year, int month, double totalRevenue) {
        this.revenueID = revenueID;
        this.year = year;
        this.month = month;
        this.totalRevenue = totalRevenue;
    }

    public String getRevenueID() {
        return revenueID;
    }

    public void setRevenueID(String revenueID) {
        this.revenueID = revenueID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
