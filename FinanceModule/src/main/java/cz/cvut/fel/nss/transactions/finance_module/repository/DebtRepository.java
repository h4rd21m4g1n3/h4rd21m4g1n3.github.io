package cz.cvut.fel.nss.transactions.finance_module.repository;

import cz.cvut.fel.nss.transactions.finance_module.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Integer> {
    @Query("SELECT e FROM Debt e WHERE e.id = :debtId AND e.userId = :userId")
    Optional<Debt> findByIdAndUserId(@Param("debtId") int debtId, @Param("userId") int userId);
    List<Debt> findAllByUserId(int userId);
}