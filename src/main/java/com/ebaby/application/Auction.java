package com.ebaby.application;

import java.util.Date;

public class Auction {
    private User user;
    private String itemDesc;
    private Double price;
    private Date startTime;
    private Date endTime;


    public Auction(User user, String itemDesc, Double price, Date startTime, Date endTime) {
        if(user.getRole() != User.Role.SELLER){
            throw new IllegalArgumentException("User must be seller");
        }
        this.user = user;
        this.itemDesc = itemDesc;
        this.price = price;
        this.startTime= startTime;
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }



}
