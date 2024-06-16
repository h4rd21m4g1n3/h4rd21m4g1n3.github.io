package cz.cvut.fel.nss.transactions.financemodule.memento;

import cz.cvut.fel.nss.transactions.financemodule.entity.GoalMementoEntity;
import cz.cvut.fel.nss.transactions.financemodule.repository.GoalMementoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GoalCaretaker {

    //private List<GoalMemento> mementoList = new ArrayList<>();
    private final GoalMementoRepository goalMementoRepository;

    @Autowired
    public GoalCaretaker(GoalMementoRepository goalMementoRepository) {
        this.goalMementoRepository = goalMementoRepository;
    }

    @Transactional
    public void addMemento(GoalMementoEntity memento) {
        goalMementoRepository.save(memento);
    }

    public List<GoalMementoEntity> getMementoList(int goalId) {
        return goalMementoRepository.findByGoalId(goalId);
    }

}
