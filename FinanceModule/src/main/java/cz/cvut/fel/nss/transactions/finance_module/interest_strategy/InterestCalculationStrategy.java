package cz.cvut.fel.nss.transactions.finance_module.interest_strategy;

public interface InterestCalculationStrategy {
    float calculateInterest(float amount, long daysBetween, int interestRate);
}
