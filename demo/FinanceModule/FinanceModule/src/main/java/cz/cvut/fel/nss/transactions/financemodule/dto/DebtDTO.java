package cz.cvut.fel.nss.transactions.financemodule.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DebtDTO {
    private int id;
    private String name;
    private float amount;
    private String nameOfPersonToGiveBack;
    private LocalDate dueDate;
    private LocalDate fromDate;
    private int interestRate;
}