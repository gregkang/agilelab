package com.ebaby.application;

import com.ebaby.services.PostOffice;

public class UnsoldAuctionNotifier extends AuctionNotifier {

    PostOffice postOffice = PostOffice.getInstance();

    public UnsoldAuctionNotifier(Auction auction) {
        super(auction);
    }

    public void notifyCloseAuction() {
        postOffice.sendEMail(auction.getUser().getUserEmail(),
                String.format("Sorry, your auction for \"%s\" did not have any bidders", auction.getItemDesc()));
    }
}
