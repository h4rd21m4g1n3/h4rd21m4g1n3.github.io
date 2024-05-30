package cz.cvut.fel.nss.transactions.financemodule.interest_strategy;

public class CompoundInterestCalculationStrategy implements InterestCalculationStrategy {

    @Override
    public float calculateInterest(float amount, long daysBetween, int interestRate) {
        float rate = interestRate / 100.0f;
        float timeInYears = daysBetween / 365.0f;
        return amount * (float) Math.pow(1 + rate, timeInYears) - amount;
    }

}
