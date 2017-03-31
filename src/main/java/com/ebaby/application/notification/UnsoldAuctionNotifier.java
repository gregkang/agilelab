package com.ebaby.application.notification;

import com.ebaby.application.Auction;
import com.ebaby.application.OnCloseProcessor;
import com.ebaby.services.PostOffice;

public class UnsoldAuctionNotifier implements OnCloseProcessor {

    PostOffice postOffice = PostOffice.getInstance();

    @Override
    public void process(Auction auction) {
        postOffice.sendEMail(auction.getUser().getUserEmail(),
                String.format("Sorry, your auction for \"%s\" did not have any bidders", auction.getItemDesc()));
    }
}
