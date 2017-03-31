package com.ebaby.application.calculation;

import com.ebaby.application.Auction;
import com.ebaby.application.OnCloseProcessor;

public class ShippingOnCloseProcessor implements OnCloseProcessor {
    private Double shippingFee;

    public ShippingOnCloseProcessor(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    @Override
    public void process(Auction auction) {
        double amount = shippingFee + auction.getCurrentHighBid();
        auction.setBuyerAmount(amount);
    }
}
