package com.ebaby.application.calculation;

import com.ebaby.application.Auction;
import com.ebaby.application.OnCloseProcessor;

public class TransactionOnCloseProcessor implements OnCloseProcessor {
    public Double getTransactionPercentage() {
        return transactionPercentage;
    }

    private Double transactionPercentage;

    public TransactionOnCloseProcessor(Double transactionPercentage) {
        this.transactionPercentage = transactionPercentage;
    }

    @Override
    public void process(Auction auction) {
        Double amount = auction.getSellerAmount() - (auction.getCurrentHighBid() * getTransactionPercentage() / 100);
        auction.setSellerAmount(amount);
    }
}
