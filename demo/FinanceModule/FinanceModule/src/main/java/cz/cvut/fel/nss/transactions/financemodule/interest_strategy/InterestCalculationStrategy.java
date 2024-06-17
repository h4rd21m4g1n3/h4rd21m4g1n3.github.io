package cz.cvut.fel.nss.transactions.financemodule.interest_strategy;

public interface InterestCalculationStrategy {
    float calculateInterest(float amount, long daysBetween, int interestRate);
}
