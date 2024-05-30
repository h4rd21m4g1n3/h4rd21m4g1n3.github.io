package cz.cvut.fel.nss.transactions.financemodule.config;


import cz.cvut.fel.nss.transactions.financemodule.interest_strategy.InterestCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public InterestCalculator interestCalculator() {
        return new InterestCalculator();
    }
}
