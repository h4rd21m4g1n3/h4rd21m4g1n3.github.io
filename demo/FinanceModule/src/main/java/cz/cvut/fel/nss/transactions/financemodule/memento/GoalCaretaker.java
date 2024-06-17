package cz.cvut.fel.nss.transactions.financemodule.memento;

import cz.cvut.fel.nss.transactions.financemodule.entity.GoalMementoEntity;
import cz.cvut.fel.nss.transactions.financemodule.repository.GoalMementoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Caretaker responsible for managing mementos of goals.
 */
@Component
public class GoalCaretaker {
    private final GoalMementoRepository goalMementoRepository;
    /**
     * Constructs a GoalCaretaker with the specified GoalMementoRepository.
     *
     * @param goalMementoRepository the repository used to manage goal mementos
     */
    @Autowired
    public GoalCaretaker(GoalMementoRepository goalMementoRepository) {
        this.goalMementoRepository = goalMementoRepository;
    }
    /**
     * Adds a memento of a goal to the repository.
     *
     * @param memento the memento to be added
     */
    public void addMemento(GoalMementoEntity memento) {
        goalMementoRepository.save(memento);
    }
    /**
     * Retrieves a list of mementos associated with a specific goal ID.
     *
     * @param goalId the ID of the goal to retrieve mementos for
     * @return the list of mementos for the specified goal ID
     */
    public List<GoalMementoEntity> getMementoList(int goalId) {
        return goalMementoRepository.findByGoalId(goalId);
    }

}
