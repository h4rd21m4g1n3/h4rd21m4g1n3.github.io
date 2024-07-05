package cz.cvut.fel.nss.transactions.transaction_module.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an income transaction.
 */
@Getter
@Setter
@Entity
@Table(name="income")
public class Income extends Transaction{

    @ManyToOne
    @JoinColumn(name = "income_category_id", nullable = false)
    private IncomeCategory incomeCategory;
}
