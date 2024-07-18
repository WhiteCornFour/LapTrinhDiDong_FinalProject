package com.example.laptrinhdidong_finalproject.Model;

public class CustomerFeedbacks {
    int feedbackID;
    String customerID;
    String feedbackContent;
    String feedbackTime;

    public CustomerFeedbacks() {

    }

    public CustomerFeedbacks(int feedbackID, String customerID, String feedbackContent, String feedbackTime) {
        this.feedbackID = feedbackID;
        this.customerID = customerID;
        this.feedbackContent = feedbackContent;
        this.feedbackTime = feedbackTime;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

}
