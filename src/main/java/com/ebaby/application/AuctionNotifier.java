package com.ebaby.application;

public abstract class AuctionNotifier {
    Auction auction;

    public AuctionNotifier(Auction auction) {
        this.auction = auction;
    }

    public abstract void notifyCloseAuction();

}
