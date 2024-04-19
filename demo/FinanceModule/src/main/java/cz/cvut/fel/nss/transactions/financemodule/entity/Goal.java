package cz.cvut.fel.nss.transactions.financemodule.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="goal")
public class Goal extends Finance{



//    @Column(name = "recurrence")
//    private Recurrence recurrence;

}
