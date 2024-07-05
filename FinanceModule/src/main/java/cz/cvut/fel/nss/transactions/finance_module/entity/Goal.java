package cz.cvut.fel.nss.transactions.finance_module.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="goal")
public class Goal extends Finance {
    public GoalMementoEntity saveToMemento() {
        return new GoalMementoEntity(getId(), getUserId(), getName(), getAmount());
    }
    public void restoreFromMemento(GoalMementoEntity memento) {
        setId(Math.toIntExact(memento.getId()));
        setUserId(memento.getUserId());
        setName(memento.getName());
        setAmount(memento.getAmount());
    }
}
