package com.ebaby.application.notification;

import com.ebaby.application.Auction;

public abstract class AuctionNotifier {
    Auction auction;

    public AuctionNotifier(Auction auction) {
        this.auction = auction;
    }

    public abstract void notifyCloseAuction();

}
