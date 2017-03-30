package com.ebaby.application;

import java.util.Objects;
import org.joda.time.DateTime;

public class Auction{
    private final User seller;
    private final String itemDesc;
    private final DateTime startTime;
    private final DateTime endTime;
    private Double price;
    private User highestBidder;
    private boolean isActive;
    private Double currentHighBid;

    public Double getSellerAmount() {
        return sellerAmount;
    }

    public Double getBuyerAmount() {
        return buyerAmount;
    }

    private Double sellerAmount;
    private Double buyerAmount;

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    private Categories category;

    public enum Categories{
        Car("Car"),
        Downloadable_Software("Downloadable Software");

        Categories(String type) {
        }
    }

    public Auction(User seller, String itemDesc, Double price, DateTime startTime, DateTime endTime, Categories category) {
        if (!seller.isAuthenticated()) {
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
        this.currentHighBid = price;
        this.category = category;
    }

    public User getHighestBidder() {
        return highestBidder;
    }

    public void bid(User bidder, Double bidPrice, DateTime bidTime) {
        if (!bidder.isAuthenticated()) {
            throw new IllegalArgumentException("Bidder must be authenticated before creating auction");
        }
        if (!isActive(bidTime)) {
            throw new IllegalArgumentException("Auction is not active");
        }
        if (Objects.equals(bidder, seller)) {
            throw new IllegalArgumentException("You can't bid on your own item");
        }
        if (bidPrice < price || bidPrice < currentHighBid) {
            throw new IllegalArgumentException(("Bid price must be greater than the current highest price"));
        }
        currentHighBid = bidPrice;
        highestBidder = bidder;
    }


    public boolean isActive(DateTime bidTime) {
        if(bidTime.isBefore(endTime) || bidTime.isAfter(startTime)){
            isActive = true;
        }
        return isActive;
    }

    public void setActive(boolean active) {
        DateTime now = DateTime.now();
        if(now.isBefore(endTime) || now.isAfter(startTime)){
            isActive = false;
        }
    }

    public void onStart(){
        setActive(true);
    }

    public void onClose(){
        setActive(false);
        Double highbid = currentHighBid;
        sellerAmount = currentHighBid - (currentHighBid * 2) /100;
        if(category == Categories.Car){
            buyerAmount = currentHighBid +1000;
        }
        else if(category != Categories.Downloadable_Software){
            buyerAmount = currentHighBid + 10;
        }
        else {
            buyerAmount = currentHighBid;
        }
        if(category == Categories.Car && highbid > 50000) {
            buyerAmount = buyerAmount + (highbid * 4) / 100;
        }
        AuctionNotifier auctionNotifier = NotificationFactory.createInstance(this);
        auctionNotifier.notifyCloseAuction();
    }

    public User getUser() {
        return seller;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public Double getPrice() {
        return price;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public Double getCurrentHighBid() {
        return currentHighBid;
    }

}
