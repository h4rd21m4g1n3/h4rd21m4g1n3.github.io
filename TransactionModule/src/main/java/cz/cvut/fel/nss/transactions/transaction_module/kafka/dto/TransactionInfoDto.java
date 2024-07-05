package cz.cvut.fel.nss.transactions.transaction_module.kafka.dto;

import lombok.Data;

import java.time.LocalDate;


/**
 * Data Transfer Object (DTO) for transaction information.
 */
@Data

public class TransactionInfoDto {

    private int userId;
    private int transactionId;
    private int amount;
    private LocalDate date;
    private String name;
}
