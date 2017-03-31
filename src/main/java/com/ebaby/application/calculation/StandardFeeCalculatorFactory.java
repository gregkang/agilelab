package com.ebaby.application.calculation;

import java.util.ArrayList;
import java.util.List;

import com.ebaby.application.Auction;
import com.ebaby.application.Category;
import com.ebaby.application.OnCloseProcessor;

public class StandardFeeCalculatorFactory extends AbstractFeeCalculatorFactory {
    @Override
    public List<OnCloseProcessor> createProcessors(Auction auction) {
        List<OnCloseProcessor> processors = new ArrayList<>();
        processors.add(new TransactionOnCloseProcessor(2.0));
        if (auction.getCategory() == Category.CAR) {
            processors.add(new ShippingOnCloseProcessor(CAR_SHIPPING_FEE));
        } else if (auction.getCategory() != Category.DOWNLOADABLE_SOFTWARE) {
            processors.add(new ShippingOnCloseProcessor(STANDARD_SHIPPING_FEE));
        }
        return processors;
    }
}
