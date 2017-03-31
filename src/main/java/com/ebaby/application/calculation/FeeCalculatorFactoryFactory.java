package com.ebaby.application.calculation;

import com.ebaby.application.Auction;
import com.ebaby.application.User;

public class FeeCalculatorFactoryFactory {
    public static AbstractFeeCalculatorFactory getInstance(Auction auction) {
        if (auction.getUser().getRole() == User.Role.PREFERRED_SELLER) {
            return new PreferredFeeCalculatorFactory();
        } else {
            return new StandardFeeCalculatorFactory();
        }
    }
}
