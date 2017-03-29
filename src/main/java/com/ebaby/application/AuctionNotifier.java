package com.ebaby.application;

import com.ebaby.services.PostOffice;

public abstract class AuctionNotifier {
    Auction auction;

    public AuctionNotifier(Auction auction) {
        this.auction = auction;
    }

    public static AuctionNotifier createInstance(Auction auction) {
        if (auction.getHighestBidder() == null) {
            return new NotifyNoSale(auction);
        }
        return new NotifySale(auction);
    }

    public abstract void notifyCloseAuction();

}
