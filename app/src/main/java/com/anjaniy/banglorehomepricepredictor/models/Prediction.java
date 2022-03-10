package com.anjaniy.banglorehomepricepredictor.models;

import java.util.UUID;

public class Prediction {

    private String sqft;
    private String bhk;
    private String bath;
    private String balcony;
    private String location;
    private String price;
    private String emailAddress;
    private String uuid;

    public Prediction(){

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

    public String getEmailAddress() {
        return emailAddress;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
