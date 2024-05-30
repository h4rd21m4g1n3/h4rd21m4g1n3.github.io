package cz.cvut.fel.nss.transactions.transactionmodule.kafka.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data

public class TransactionInfoDto {

    private int userId;
    private int transactionId;
    private int amount;
    private LocalDate date;
    private String name;
}
