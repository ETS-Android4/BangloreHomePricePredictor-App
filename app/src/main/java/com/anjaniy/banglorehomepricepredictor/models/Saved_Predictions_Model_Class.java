package com.anjaniy.banglorehomepricepredictor.models;

public class Saved_Predictions_Model_Class {

    private String id;
    private String sqft;
    private String bhk;
    private String bath;
    private String balcony;
    private String location;
    private String price;

    public Saved_Predictions_Model_Class(){

    }

    public Saved_Predictions_Model_Class(String id,String sqft, String bhk, String bath, String balcony, String location, String price) {
        this.id = id;
        this.sqft = sqft;
        this.bhk = bhk;
        this.bath = bath;
        this.balcony = balcony;
        this.location = location;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSqft() {
        return sqft;
    }

    public void setSqft(String sqft) {
        this.sqft = sqft;
    }

    public String getBhk() {
        return bhk;
    }

    public void setBhk(String bhk) {
        this.bhk = bhk;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getBalcony() {
        return balcony;
    }

    public void setBalcony(String balcony) {
        this.balcony = balcony;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
