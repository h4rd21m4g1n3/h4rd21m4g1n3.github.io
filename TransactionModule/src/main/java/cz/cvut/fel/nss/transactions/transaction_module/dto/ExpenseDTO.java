package cz.cvut.fel.nss.transactions.transaction_module.dto;

import cz.cvut.fel.nss.transactions.transaction_module.entity.ExpenseCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


/**
 * Data Transfer Object (DTO) for expense transactions.
 */
@Getter
@Setter
public class ExpenseDTO {
    private int id;
    private float amount;
    private String name;
    private LocalDate transactionDate;
    private ExpenseCategory expenseCategory;
}
