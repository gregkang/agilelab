package com.ebaby.application;

public class FeeCalculatorDecorator implements FeeCalculator {
    private FeeCalculator nextCalculator;

    public FeeCalculatorDecorator(FeeCalculator nextCalculator) {
        this.nextCalculator = nextCalculator;
    }

    @Override
    public Double calculate(Auction auction) {
        if(nextCalculator == null){
            return 0.0;
        }
        return nextCalculator.calculate(auction);
    }
}
