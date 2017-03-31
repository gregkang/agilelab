package com.ebaby.application.calculation;

import java.util.ArrayList;
import java.util.List;

import com.ebaby.application.Auction;
import com.ebaby.application.OnCloseProcessor;
import com.ebaby.application.calculation.LuxuryTaxOnCloseProcessor;

public abstract class AbstractFeeCalculatorFactory {
    protected static final double CAR_SHIPPING_FEE = 1000.0;
    protected static final double STANDARD_SHIPPING_FEE = 10.0;

    public List<OnCloseProcessor> createProcessors(Auction auction) {
        List<OnCloseProcessor> processors = new ArrayList<>();
        if (auction.getCurrentHighBid() > 50000.0) {
            processors.add(new LuxuryTaxOnCloseProcessor(4.0));
        }
        return processors;
    }
}
