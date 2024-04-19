package cz.cvut.fel.nss.transactions.financemodule.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="debt")
public class Debt {

    @Basic
    @Column(name = "nameOfPersonToGiveBack", nullable = false)
    private String nameOfPersonToGiveBack;


    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;



}
