package cz.cvut.fel.nss.transactions.financemodule.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoalDTO {
    private int id;
    private String name;
    private float amount;
}
