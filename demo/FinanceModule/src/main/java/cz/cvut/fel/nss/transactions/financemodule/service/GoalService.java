package cz.cvut.fel.nss.transactions.financemodule.service;


import cz.cvut.fel.nss.transactions.financemodule.entity.Goal;
import cz.cvut.fel.nss.transactions.financemodule.entity.GoalMementoEntity;
import cz.cvut.fel.nss.transactions.financemodule.kafka.dto.TransactionInfoDto;
import cz.cvut.fel.nss.transactions.financemodule.memento.GoalCaretaker;
import cz.cvut.fel.nss.transactions.financemodule.repository.GoalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@Transactional
public class GoalService {
    private final GoalRepository goalRepository;
    private final GoalCaretaker caretaker;
    /**
     * Constructs a new GoalService with the specified GoalRepository and GoalCaretaker.
     *
     * @param goalRepository the repository used to manage goals
     * @param caretaker      the caretaker for goal mementos
     */
    @Autowired
    public GoalService(GoalRepository goalRepository, GoalCaretaker caretaker) {
        this.goalRepository = goalRepository;
        this.caretaker = caretaker;
    }

    /**
     * Retrieves a goal by its ID and the user's ID.
     *
     * @param goalId the ID of the goal
     * @param userId the ID of the user
     * @return the goal with the specified ID for the specified user
     * @throws RuntimeException if the goal is not found
     */
    public Goal getGoalById(int goalId, int userId){
        Optional<Goal> optionalIncome = goalRepository.findByIdAndUserId(goalId, userId);
        return optionalIncome.orElseThrow(() -> new RuntimeException("Goal not found with id: " + goalId + " for user: " + userId));
    }
    /**
     * Creates a new goal.
     *
     * @param goal the goal to be created
     * @throws NullPointerException if the goal is null
     */
    @Transactional
    public void createGoal(Goal goal) {
        Objects.requireNonNull(goal);
        goalRepository.save(goal);
    }
    /**
     * Updates an existing goal.
     *
     * @param updatedGoal the updated goal information
     * @param userId      the ID of the user
     * @throws NullPointerException if the updated goal is null
     */
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
    /**
     * Deletes a goal by its ID and the user's ID.
     *
     * @param goalId the ID of the goal to be deleted
     * @param userId the ID of the user
     * @throws RuntimeException if the goal is not found
     */
    @Transactional
    public void deleteGoal(int goalId, int userId) {
        getGoalById(goalId, userId);
        goalRepository.deleteById(goalId);
    }
    /**
     * Retrieves all goals for a specified user.
     *
     * @param userId the ID of the user
     * @return a list of goals for the specified user
     */
    public List<Goal> getAllGoals(int userId) {
        return goalRepository.findAllByUserId(userId);
    }
    /**
     * Retrieves the history of mementos for a specified goal.
     *
     * @param goalId the ID of the goal
     * @return a list of goal mementos
     */
    public List<GoalMementoEntity> getGoalHistory(int goalId) {
        return caretaker.getMementoList(goalId);
    }
    /**
     * Updates goals with the information from a transaction.
     *
     * @param transactionInfo the transaction information
     */
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
