package com.ebaby.application;

public class AuctionNotifierFactory {
    public static AuctionNotifier createInstance(Auction auction) {
        if (auction.getHighestBidder() == null) {
            return new UnsoldAuctionNotifier(auction);
        }
        return new SoldAuctionNotifier(auction);
    }
}
