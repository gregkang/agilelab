package com.ebaby.application;

import java.util.Objects;

import org.joda.time.DateTime;

public class Auction {
    private User seller;
    private String itemDesc;
    private Double price;
    private DateTime startTime;
    private DateTime endTime;
    private User highestBidder;

    public Auction(User seller, String itemDesc, Double price, DateTime startTime, DateTime endTime) {
        if(!seller.isAuthenticated()){
            throw new IllegalArgumentException("Seller must be authenticated before creating auction");
        }
        if (seller.getRole() != User.Role.SELLER) {
            throw new IllegalArgumentException("User must be seller");
        }

        if (startTime.isBefore(DateTime.now())) {
            throw new IllegalArgumentException("startTime must be after now");
        }

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }
        this.seller = seller;
        this.itemDesc = itemDesc;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public User getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(User highestBidder) {
        this.highestBidder = highestBidder;
    }

    public void bid(User bidder, Double bidPrice, DateTime bidTime){
        if(!bidder.isAuthenticated()){
            throw new IllegalArgumentException("Bidder must be authenticated before creating auction");
        }
        if(bidTime.isAfter(endTime) || bidTime.isBefore(startTime)){
            throw new IllegalArgumentException("Auction is not active");
        }
        if(Objects.equals(bidder, seller)){
            throw new IllegalArgumentException("You can't bid on your own item");
        }
        if(bidPrice <= price){
            throw new IllegalArgumentException(("Bid price must be greater than the current highest price"));
        }
        price = bidPrice;
        highestBidder = bidder;
    }

    public User getUser() {
        return seller;
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

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

}
