package com.ebaby.application.notification;

import com.ebaby.application.Auction;
import com.ebaby.application.OnCloseProcessor;
import com.ebaby.services.PostOffice;

public class SoldAuctionNotifier implements OnCloseProcessor {
    PostOffice postOffice = PostOffice.getInstance();

    @Override
    public void process(Auction auction) {
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
