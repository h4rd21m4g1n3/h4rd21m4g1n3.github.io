package cz.cvut.fel.nss.transactions.financemodule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "goal_memento")
@Getter
@Setter
public class GoalMementoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int goalId;
    private int userId;
    private String name;
    private float amount;

    public GoalMementoEntity() {}

    public GoalMementoEntity(int goalId, int userId, String name, float amount) {
        this.goalId = goalId;
        this.userId = userId;
        this.name = name;
        this.amount = amount;
    }
}
