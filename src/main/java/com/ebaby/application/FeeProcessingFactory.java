package com.ebaby.application;

/**
 * Created by gkang on 3/30/2017.
 */
public class FeeProcessingFactory {
    public static FeeCalculator createInstance(Auction auction, User.Role role) {
        if (role == User.Role.SELLER) {
            return new TransactionFeeCalculator(null, 2.0);
        } else {
            if (auction.getCategory() == Auction.Categories.Car) {
                return new ShippingFeeCalculator(new LuxuryTaxFeeCalculator(null, 50000.0, 4.0), 1000.0);
            } else if (auction.getCategory() != Auction.Categories.Downloadable_Software) {
                return new ShippingFeeCalculator(null, 10.0);
            } else {
                return new FeeCalculatorDecorator(null);
            }
        }
    }
}
