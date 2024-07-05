package cz.cvut.fel.nss.transactions.transactionmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionModule {
    public static void main(String[] args) {
        System.out.println("Goodbye world!");
        SpringApplication.run(TransactionModule.class, args);
    }
}