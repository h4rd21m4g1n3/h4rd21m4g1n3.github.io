package cz.cvut.fel.nss.transactions.transaction_module.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


/**
 * Represents a generic transaction.
 * This is an abstract class that is inherited by specific transaction types such as Income and Expense.
 */
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="transaction")
public abstract class Transaction {

    @Column(name = "user_id", nullable = false)
    private int userId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "amount", nullable = false)
    private float amount;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;



}
