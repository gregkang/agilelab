package com.ebaby.application;

public class NotificationFactory {
    public static AuctionNotifier createInstance(Auction auction) {
        if (auction.getHighestBidder() == null) {
            return new NotifyNoSale(auction);
        }
        return new NotifySale(auction);
    }
}
