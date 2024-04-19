package cz.cvut.fel.nss.transactions.transactionmodule.repository;

import cz.cvut.fel.nss.transactions.transactionmodule.entity.Expense;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Income;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Override
    boolean existsById(Integer id);

    @Query("SELECT e FROM Transaction e ORDER BY e.transactionDate DESC")
    List<Transaction> findAllByOrderByTransactionDateDesc();

    @Query("SELECT e FROM Transaction e ORDER BY e.transactionDate ASC")
    List<Transaction> findAllByOrderByTransactionDateAsc();

    @Query("SELECT e FROM Transaction e WHERE e.amount BETWEEN :fromAmount AND :toAmount ORDER BY e.amount ASC")
    List<Transaction> findByAmountBetweenOrderByAmountAsc(float fromAmount, float toAmount);
}
