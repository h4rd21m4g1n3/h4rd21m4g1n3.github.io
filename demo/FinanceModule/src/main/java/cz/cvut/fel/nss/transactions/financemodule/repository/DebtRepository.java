package cz.cvut.fel.nss.transactions.financemodule.repository;

import cz.cvut.fel.nss.transactions.financemodule.entity.Debt;
import cz.cvut.fel.nss.transactions.financemodule.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Integer> {
    @Query("SELECT e FROM Debt e WHERE e.id = :debtId AND e.userId = :userId")
    Optional<Debt> findByIdAndUserId(@Param("debtId") int debtId, @Param("userId") int userId);
}