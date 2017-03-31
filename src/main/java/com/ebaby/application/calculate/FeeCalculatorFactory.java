package com.ebaby.application.calculate;

import com.ebaby.application.Auction;
import com.ebaby.application.User;

public class FeeCalculatorFactory {
    public static FeeCalculator createInstance(Auction auction, User.Role role) {
        if (role == User.Role.SELLER) {
            return new TransactionFeeCalculator(2.0);
        } else {
            if (auction.getCategory() == Auction.Categories.Car) {
                return new ShippingFeeCalculator(new LuxuryTaxFeeCalculator(50000.0, 4.0), 1000.0);
            } else if (auction.getCategory() != Auction.Categories.Downloadable_Software) {
                return new ShippingFeeCalculator(10.0);
            } else {
                return new ShippingFeeCalculator(0.0);
            }
        }
    }
}
