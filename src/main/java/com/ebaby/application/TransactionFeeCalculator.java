package com.ebaby.application;

/**
 * Created by gkang on 3/30/2017.
 */
public class TransactionFeeCalculator extends FeeCalculatorDecorator {
    public Double getTransactionPercentage() {
        return transactionPercentage;
    }

    private Double transactionPercentage;

    public TransactionFeeCalculator(FeeCalculator nextCalculator, Double transactionPercentage) {
        super(nextCalculator);
        this.transactionPercentage = transactionPercentage;
    }

    @Override
    public Double calculate(Auction auction) {
        Double result  = auction.getCurrentHighBid() * getTransactionPercentage()/100;
        return super.calculate(auction) - result;
    }
}
