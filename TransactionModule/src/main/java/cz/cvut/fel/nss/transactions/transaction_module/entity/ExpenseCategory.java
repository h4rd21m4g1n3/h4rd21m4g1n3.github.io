package cz.cvut.fel.nss.transactions.transaction_module.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a category of expenses.
 */
@Getter
@Setter
@Entity
@Table(name="expense_category")
public class ExpenseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense_category_name", nullable = false)
    private String categoryName;
}
