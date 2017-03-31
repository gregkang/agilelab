package com.ebaby.application.calculate;

public class FeeCalculatorDecorator implements FeeCalculator {
    private FeeCalculator nextCalculator;

    public FeeCalculatorDecorator(FeeCalculator nextCalculator) {
        this.nextCalculator = nextCalculator;
    }

    @Override
    public Double calculate(Double highestBid) {
        if (nextCalculator == null) {
            return highestBid;
        }
        return nextCalculator.calculate(highestBid);
    }
}
