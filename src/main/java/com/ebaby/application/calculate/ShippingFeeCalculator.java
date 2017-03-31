package com.ebaby.application.calculate;

public class ShippingFeeCalculator extends FeeCalculatorDecorator {
    private Double shippingFee;

    public ShippingFeeCalculator(Double shippingFee) {
        this(null, shippingFee);
    }

    public ShippingFeeCalculator(FeeCalculator nextCalculator, Double shippingFee) {
        super(nextCalculator);
        this.shippingFee = shippingFee;
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    @Override
    public Double calculate(Double highestBid) {
        return getShippingFee() + super.calculate(highestBid);
    }

}
