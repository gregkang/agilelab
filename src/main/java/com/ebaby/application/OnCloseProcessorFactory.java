package com.ebaby.application;

import java.util.ArrayList;
import java.util.List;

import com.ebaby.application.calculation.AbstractFeeCalculatorFactory;
import com.ebaby.application.calculation.FeeCalculatorFactoryFactory;
import com.ebaby.application.calculation.LuxuryTaxOnCloseProcessor;
import com.ebaby.application.log.OffHoursLogger;
import com.ebaby.application.log.SalesLogger;
import com.ebaby.application.notification.SoldAuctionNotifier;
import com.ebaby.application.notification.UnsoldAuctionNotifier;

public class OnCloseProcessorFactory {


    public static List<OnCloseProcessor> createProcessors(Auction auction) {
        List<OnCloseProcessor> processors = new ArrayList<>();

        // calculations
        AbstractFeeCalculatorFactory calculatorFactory = FeeCalculatorFactoryFactory.getInstance(auction);
        processors.addAll(calculatorFactory.createProcessors(auction));

        // notifications
        if (auction.getHighestBidder() == null) {
            processors.add(new UnsoldAuctionNotifier());
        } else {
            processors.add(new SoldAuctionNotifier());
        }

        // loggers
        if (auction.getCategory() == Category.CAR) {
            processors.add(new SalesLogger("CarSales.txt"));
        }
        if (auction.getCurrentHighBid() > 10000) {
            processors.add(new SalesLogger("SalesOver10K.txt"));
        }
        processors.add(new OffHoursLogger("OffHourLogger.txt", auction.getHours()));
        return processors;
    }

    public List<OnCloseProcessor> getCalculatorProcessors(Auction auction) {
        List<OnCloseProcessor> processors = new ArrayList<>();
        if (auction.getCurrentHighBid() > 50000.0) {
            processors.add(new LuxuryTaxOnCloseProcessor(4.0));
        }
        return processors;
    }
}
