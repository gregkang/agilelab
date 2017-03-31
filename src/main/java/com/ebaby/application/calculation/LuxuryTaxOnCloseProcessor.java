package com.ebaby.application.calculation;

import com.ebaby.application.Auction;
import com.ebaby.application.OnCloseProcessor;

public class LuxuryTaxOnCloseProcessor implements OnCloseProcessor {
    private Double taxPercentage;

    public LuxuryTaxOnCloseProcessor(Double taxAmount) {
        this.taxPercentage = taxAmount;
    }

    @Override
    public void process(Auction auction) {
        Double amount = auction.getCurrentHighBid() * (taxPercentage / 100) + auction.getBuyerAmount();
        auction.setBuyerAmount(amount);
    }
}
