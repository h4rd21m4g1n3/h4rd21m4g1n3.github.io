package cz.cvut.fel.nss.transactions.financemodule.service;


import cz.cvut.fel.nss.transactions.financemodule.entity.Debt;
import cz.cvut.fel.nss.transactions.financemodule.entity.Goal;
import cz.cvut.fel.nss.transactions.financemodule.entity.GoalMementoEntity;
import cz.cvut.fel.nss.transactions.financemodule.kafka.dto.TransactionInfoDto;
import cz.cvut.fel.nss.transactions.financemodule.memento.GoalCaretaker;

import cz.cvut.fel.nss.transactions.financemodule.repository.GoalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@Transactional
public class GoalService {

    private final GoalRepository goalRepository;
    private final GoalCaretaker caretaker;
    //private List<GoalMemento> mementoList = new ArrayList<>();

    @Autowired
    public GoalService(GoalRepository goalRepository, GoalCaretaker caretaker) {
        this.goalRepository = goalRepository;
        this.caretaker = caretaker;
    }

    public Goal getGoalById(int goalId, int userId){
        Optional<Goal> optionalIncome = goalRepository.findByIdAndUserId(goalId, userId);
        return optionalIncome.orElseThrow(() -> new RuntimeException("Goal not found with id: " + goalId + " for user: " + userId));
    }


    @Transactional
    public void createGoal(Goal goal) {
        Objects.requireNonNull(goal);
        goalRepository.save(goal);
    }

    @Transactional
    @CachePut(value = "goals", key = "#updatedGoal.id")
    public void updateGoal(Goal updatedGoal, int userId) {
        Objects.requireNonNull(updatedGoal);
        Goal existingGoal = getGoalById(updatedGoal.getId(), userId);

        GoalMementoEntity memento = new GoalMementoEntity(existingGoal.getId(), existingGoal.getUserId(), existingGoal.getName(), existingGoal.getAmount());
        caretaker.addMemento(memento);

        existingGoal.setAmount(updatedGoal.getAmount());
        existingGoal.setName(updatedGoal.getName());
        goalRepository.save(existingGoal);
    }

    @Transactional
    public void deleteGoal(int goalId, int userId) {
        getGoalById(goalId, userId);
        goalRepository.deleteById(goalId);
    }



    public List<Goal> getAllGoals(int userId) {
        return goalRepository.findAllByUserId(userId);
    }
    public List<GoalMementoEntity> getGoalHistory(int goalId) {
        return caretaker.getMementoList(goalId);
    }

    @Transactional
    public void updateGoalWithTransaction(TransactionInfoDto transactionInfo) {
        log.info("Updating goal with transaction for user: {} with transaction info: {}", transactionInfo);
        int userId= transactionInfo.getUserId();
        List<Goal> goals = getAllGoals(userId);

        for (Goal goal : goals) {

            goal.setAmount(goal.getAmount() + transactionInfo.getAmount());
            log.info("Goal for user: {} with amount: {}", goal.getAmount());

            goalRepository.save(goal);

            GoalMementoEntity memento = new GoalMementoEntity(goal.getId(), goal.getUserId(), goal.getName(), goal.getAmount());
            caretaker.addMemento(memento);
        }

    }


}
