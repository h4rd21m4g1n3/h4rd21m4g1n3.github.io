package cz.cvut.fel.nss.transactions.financemodule.repository;

import cz.cvut.fel.nss.transactions.financemodule.entity.GoalMementoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GoalMementoRepository extends JpaRepository<GoalMementoEntity, Long> {
    List<GoalMementoEntity> findByGoalId(int goalId);
}