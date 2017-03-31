package com.ebaby.application.calculate;

public class LuxuryTaxFeeCalculator extends FeeCalculatorDecorator {
    private Double taxThreshold;
    private Double taxPercentage;

    public LuxuryTaxFeeCalculator(Double taxThreshold, Double taxAmount) {
        this(null, taxThreshold, taxAmount);
    }

    public LuxuryTaxFeeCalculator(FeeCalculator nextCalculator, Double taxThreshold, Double taxAmount) {
        super(nextCalculator);
        this.taxThreshold = taxThreshold;
        this.taxPercentage = taxAmount;
    }

    @Override
    public Double calculate(Double highestBid) {
        Double result = 0.0;
        if (highestBid > taxThreshold) {
            result = highestBid * (taxPercentage / 100);
        }
        return result + super.calculate(highestBid);
    }

    public Double getTaxThreshold() {
        return taxThreshold;
    }

    public Double getTaxAmount() {
        return taxPercentage;
    }
}
