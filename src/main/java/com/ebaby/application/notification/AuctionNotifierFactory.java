package com.ebaby.application.notification;

import com.ebaby.application.Auction;

public class AuctionNotifierFactory {
    public static AuctionNotifier createInstance(Auction auction) {
        if (auction.getHighestBidder() == null) {
            return new UnsoldAuctionNotifier(auction);
        }
        return new SoldAuctionNotifier(auction);
    }
}
