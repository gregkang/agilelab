package com.ebaby.application.log;

import com.ebaby.application.Auction;
import com.ebaby.application.OnCloseProcessor;
import com.ebaby.services.AuctionLogger;
import com.ebaby.services.Hours;

public class OffHoursLogger implements OnCloseProcessor {
    private String filename;
    private Hours hours;
    private AuctionLogger auctionLogger;

    public OffHoursLogger(String filename, Hours hours) {
        this.filename = filename;
        this.hours = hours;
        auctionLogger = AuctionLogger.getInstance();
    }

    @Override
    public void process(Auction auction) {
        if (hours.isOffHours()) {
            auctionLogger.log(filename,
                    String.format("Item \"%s\" sold to buyer for \"%s\"",
                            auction.getItemDesc(),
                            auction.getBuyerAmount()));

        }
    }
}
