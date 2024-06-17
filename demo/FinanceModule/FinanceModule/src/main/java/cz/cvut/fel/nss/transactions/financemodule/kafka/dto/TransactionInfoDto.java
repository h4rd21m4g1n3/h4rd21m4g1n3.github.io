package cz.cvut.fel.nss.transactions.financemodule.kafka.dto;

import lombok.Data;

import java.time.LocalDate;

@Data

public class TransactionInfoDto {

    private int userId;
    private int transactionId;
    private int amount;
    private LocalDate date;
    private String name;
}

