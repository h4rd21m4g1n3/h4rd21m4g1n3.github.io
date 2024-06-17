package cz.cvut.fel.nss.transactions.financemodule.repository;
import cz.cvut.fel.nss.transactions.financemodule.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {

    List<Goal> findAllByUserId(int userId);
    @Query("SELECT e FROM Goal e WHERE e.id = :goalId AND e.userId = :userId")
    Optional<Goal> findByIdAndUserId(@Param("goalId") int goalId, @Param("userId") int userId);
}
