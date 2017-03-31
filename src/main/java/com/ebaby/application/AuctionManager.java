package com.ebaby.application;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.ebaby.services.Auctionable;

public class AuctionManager implements Auctionable {
    private List<Auction> auctions;
    private Users users;

    public AuctionManager() {
        auctions = new ArrayList<>();
        users = new Users();
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public Users getUsers() {
        return users;
    }

    @Override
    public void handleAuctionEvents(long now) {
        DateTime time = new DateTime(now);
        for (Auction auction : auctions) {
            if (!auction.isActive() && time.isAfter(auction.getStartTime())) {
                auction.onStart();
            }
            if (auction.isActive() && time.isAfter(auction.getEndTime())) {
                auction.onClose();
            }
        }
    }
}
