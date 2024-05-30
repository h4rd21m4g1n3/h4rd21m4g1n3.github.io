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

//        // Create an application context
//        ApplicationContext context = new AnnotationConfigApplicationContext("cz.cvut.fel.nss.transactions.transactionmodule");
//
//        // Retrieve the IncomeService bean from the context
//        IncomeService service = context.getBean(IncomeService.class);
//
//        // Now you can use the service
//        // For example:
//        // Transaction income = new Income();
//        // service.createIncome(income);
    }
}