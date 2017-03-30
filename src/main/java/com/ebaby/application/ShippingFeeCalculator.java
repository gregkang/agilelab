package com.ebaby.application;

/**
 * Created by gkang on 3/30/2017.
 */
public class ShippingFeeCalculator extends FeeCalculatorDecorator {
    private Double shippingFee;

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public ShippingFeeCalculator(FeeCalculator nextCalculator, Double shippingFee) {
        super(nextCalculator);
        this.shippingFee = shippingFee;
    }

    @Override
    public Double calculate(Auction auction) {
        return getShippingFee() + super.calculate(auction);
    }

}
