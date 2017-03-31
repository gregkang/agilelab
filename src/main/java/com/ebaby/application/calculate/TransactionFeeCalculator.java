package com.ebaby.application.calculate;

public class TransactionFeeCalculator extends FeeCalculatorDecorator {
    public Double getTransactionPercentage() {
        return transactionPercentage;
    }

    private Double transactionPercentage;

    public TransactionFeeCalculator(Double transactionPercentage) {
        this(null, transactionPercentage);
    }

    public TransactionFeeCalculator(FeeCalculator nextCalculator, Double transactionPercentage) {
        super(nextCalculator);
        this.transactionPercentage = transactionPercentage;
    }

    @Override
    public Double calculate(Double highestBid) {
        Double result = highestBid * getTransactionPercentage() / 100;
        return super.calculate(highestBid) - result;
    }
}
