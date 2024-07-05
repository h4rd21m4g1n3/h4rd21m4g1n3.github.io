package cz.cvut.fel.nss.transactions.finance_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FinanceModule {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(FinanceModule.class, args);
    }
}