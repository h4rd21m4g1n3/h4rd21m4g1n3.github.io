package cz.cvut.fel.nss.transactions.transactionmodule;

import cz.cvut.fel.nss.transactions.transactionmodule.entity.Income;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Transaction;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.IncomeRepository;
import cz.cvut.fel.nss.transactions.transactionmodule.service.IncomeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Goodbye world!");
        SpringApplication.run(Main.class, args);
    }
}