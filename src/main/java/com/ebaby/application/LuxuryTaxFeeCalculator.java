package com.ebaby.application;

public class LuxuryTaxFeeCalculator extends FeeCalculatorDecorator {
    private Double taxThreshold;
    private Double taxPercentage;

    public LuxuryTaxFeeCalculator(FeeCalculator nextCalculator, Double taxThreshold, Double taxAmount) {
        super(nextCalculator);
        this.taxThreshold = taxThreshold;
        this.taxPercentage = taxAmount;
    }

    @Override
    public Double calculate(Auction auction) {
        Double result = 0.0;
        if(auction.getCurrentHighBid() > taxThreshold){
            result = auction.getCurrentHighBid() * (taxPercentage / 100);
        }
        return result + super.calculate(auction);
    }

    public Double getTaxThreshold() {
        return taxThreshold;
    }

    public void setTaxThreshold(Double taxThreshold) {
        this.taxThreshold = taxThreshold;
    }

    public Double getTaxAmount() {
        return taxPercentage;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxPercentage = taxAmount;
    }
}
