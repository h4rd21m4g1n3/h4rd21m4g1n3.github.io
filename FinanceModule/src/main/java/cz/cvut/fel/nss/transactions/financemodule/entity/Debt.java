package cz.cvut.fel.nss.transactions.financemodule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="debt")
public class Debt extends Finance {
    @Column(name = "nameOfPersonToGiveBack", nullable = false)
    private String nameOfPersonToGiveBack;
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    @Column(name= "interest_rate", nullable = false)
    private int interestRate;
}
