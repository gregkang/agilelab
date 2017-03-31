package com.ebaby.application.log;

import com.ebaby.application.Auction;
import com.ebaby.application.OnCloseProcessor;
import com.ebaby.services.AuctionLogger;

public class SalesLogger implements OnCloseProcessor {
    private String filename;
    private AuctionLogger auctionLogger = AuctionLogger.getInstance();

    public SalesLogger(String filename) {
        this.filename = filename;
    }

    @Override
    public void process(Auction auction) {
        auctionLogger.log(filename,
                String.format("Item \"%s\" sold to buyer for \"%s\"", auction.getItemDesc(), auction.getBuyerAmount()));
    }
}
