package com.ebaby.application;

import com.ebaby.services.PostOffice;

public class SoldAuctionNotifier extends AuctionNotifier {
    PostOffice postOffice = PostOffice.getInstance();

    public SoldAuctionNotifier(Auction auction) {
        super(auction);
    }

    public void notifyCloseAuction() {
        postOffice.sendEMail(auction.getUser().getUserEmail(),
                String.format(" Your \"%s\" auction sold to bidder \"%s\" for \"%s\"",
                        auction.getItemDesc(),
                        auction.getHighestBidder().getUserEmail(),
                        auction.getCurrentHighBid()));
        postOffice.sendEMail(auction.getHighestBidder().getUserEmail(),
                String.format("Congratulations! you won an auction for a \"%s\" from \"%s\" for \"%s\"",
                        auction.getItemDesc(),
                        auction.getUser().getUserEmail(),
                        auction.getCurrentHighBid()));

    }
}
