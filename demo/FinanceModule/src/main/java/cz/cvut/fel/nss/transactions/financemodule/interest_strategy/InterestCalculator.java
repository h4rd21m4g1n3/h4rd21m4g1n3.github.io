package cz.cvut.fel.nss.transactions.financemodule.interest_strategy;

public class InterestCalculator {
    private InterestCalculationStrategy strategy;

    public void setStrategy(InterestCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public float calculateInterest(float amount, long daysBetween, int interestRate) {
        return strategy.calculateInterest(amount, daysBetween, interestRate);
    }
}
