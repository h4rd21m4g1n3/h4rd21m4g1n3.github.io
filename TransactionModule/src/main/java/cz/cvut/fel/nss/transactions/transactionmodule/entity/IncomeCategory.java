package cz.cvut.fel.nss.transactions.transactionmodule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a category of incomes.
 */
@Getter
@Setter
@Entity
@Table(name="income_category")
public class IncomeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "income_category_name", nullable = false)
    private String categoryName;
}
