package com.ebaby.application;

import java.util.List;
import java.util.Objects;

import org.joda.time.DateTime;

import com.ebaby.services.AuctionLogger;

public class Auction {
    private final User seller;
    private final String itemDesc;
    private final DateTime startTime;
    private final DateTime endTime;
    private Double price;
    private User highestBidder;
    private boolean isActive;
    private Double currentHighBid;
    private Double sellerAmount;
    private Double buyerAmount;


    public Double getSellerAmount() {
        return sellerAmount;
    }

    public void setSellerAmount(Double sellerAmount) {
        this.sellerAmount = sellerAmount;
    }

    public Double getBuyerAmount() {
        return buyerAmount;
    }

    public void setBuyerAmount(Double buyerAmount) {
        this.buyerAmount = buyerAmount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private Category category;

    public Auction(User seller,
            String itemDesc,
            Double price,
            DateTime startTime,
            DateTime endTime,
            Category category) {
        if (!seller.isAuthenticated()) {
            throw new IllegalArgumentException("Seller must be authenticated before creating auction");
        }
        if (!seller.isASeller()) {
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

    public void bid(User bidder, Double bidPrice) {
        if (!bidder.isAuthenticated()) {
            throw new IllegalArgumentException("Bidder must be authenticated before creating auction");
        }
        if (!isActive()) {
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void onStart() {
        setActive(true);
    }

    public void onClose() {
        setActive(false);
        setBuyerAmount(getCurrentHighBid());
        setSellerAmount(getCurrentHighBid());
        List<OnCloseProcessor> processorList = OnCloseProcessorFactory.createProcessors(this);
        for (OnCloseProcessor processor : processorList) {
            processor.process(this);
        }
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
