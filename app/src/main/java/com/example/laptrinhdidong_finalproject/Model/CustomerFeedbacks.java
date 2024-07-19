package com.example.laptrinhdidong_finalproject.Model;

public class CustomerFeedbacks {
    String feedbackID;
    String customerID;
    String feedbackContent;
    String feedbackTime;

    public CustomerFeedbacks() {

    }

    public CustomerFeedbacks(String feedbackID, String customerID, String feedbackContent, String feedbackTime) {
        this.feedbackID = feedbackID;
        this.customerID = customerID;
        this.feedbackContent = feedbackContent;
        this.feedbackTime = feedbackTime;
    }

    public CustomerFeedbacks(String customerID, String feedbackContent, String feedbackTime) {
        this.customerID = customerID;
        this.feedbackContent = feedbackContent;
        this.feedbackTime = feedbackTime;
        this.feedbackID = generateFeedbackID(customerID);
    }

    private String generateFeedbackID(String customerID){
        return "F" + customerID;
    }
    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
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
