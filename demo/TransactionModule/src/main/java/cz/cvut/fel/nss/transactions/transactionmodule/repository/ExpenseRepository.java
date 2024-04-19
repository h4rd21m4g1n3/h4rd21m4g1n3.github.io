package cz.cvut.fel.nss.transactions.transactionmodule.repository;

import cz.cvut.fel.nss.transactions.transactionmodule.entity.Expense;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.ExpenseCategory;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByExpenseCategory(ExpenseCategory expenseCategory);

    @Query("SELECT e FROM Expense e ORDER BY e.transactionDate DESC")
    List<Expense> findAllByOrderByTransactionDateDesc();

    @Query("SELECT e FROM Expense e ORDER BY e.transactionDate ASC")
    List<Expense> findAllByOrderByTransactionDateAsc();

    @Query("SELECT e FROM Expense e WHERE e.amount BETWEEN :fromAmount AND :toAmount ORDER BY e.amount ASC")
    List<Expense> findByAmountBetweenOrderByAmountAsc(float fromAmount, float toAmount);
}
