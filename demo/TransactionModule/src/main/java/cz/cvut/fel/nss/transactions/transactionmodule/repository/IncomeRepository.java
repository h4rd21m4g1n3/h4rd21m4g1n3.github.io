package cz.cvut.fel.nss.transactions.transactionmodule.repository;

import cz.cvut.fel.nss.transactions.transactionmodule.entity.Expense;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Income;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
    boolean existsById(Long id);
    List<Income> findByIncomeCategory(IncomeCategory incomeCategory);

    @Query("SELECT e FROM Income e ORDER BY e.transactionDate DESC")
    List<Income> findAllByOrderByTransactionDateDesc();

    @Query("SELECT e FROM Income e ORDER BY e.transactionDate ASC")
    List<Income> findAllByOrderByTransactionDateAsc();

    @Query("SELECT e FROM Income e WHERE e.amount BETWEEN :fromAmount AND :toAmount ORDER BY e.amount ASC")
    List<Income> findByAmountBetweenOrderByAmountAsc(float fromAmount, float toAmount);
    @Query("SELECT i FROM Income i WHERE i.amount >= :fromAmount ORDER BY i.amount ASC")
    List<Income> findByAmountGreaterThanEqualOrderByAmountAsc(@Param("fromAmount") float fromAmount);
}
