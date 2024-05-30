package cz.cvut.fel.nss.transactions.financemodule.controller;

import cz.cvut.fel.nss.transactions.financemodule.dto.DebtDTO;
import cz.cvut.fel.nss.transactions.financemodule.entity.Debt;
import cz.cvut.fel.nss.transactions.financemodule.interest_strategy.CompoundInterestCalculationStrategy;
import cz.cvut.fel.nss.transactions.financemodule.interest_strategy.InterestCalculationStrategy;
import cz.cvut.fel.nss.transactions.financemodule.interest_strategy.InterestCalculator;
import cz.cvut.fel.nss.transactions.financemodule.interest_strategy.SimpleInterestCalculationStrategy;
import cz.cvut.fel.nss.transactions.financemodule.repository.DebtRepository;
import cz.cvut.fel.nss.transactions.financemodule.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@RestController
@RequestMapping("/finances/debts")
public class DebtController {

    private final DebtService debtService;

    @Autowired
    private DebtRepository debtRepository;

    private final InterestCalculator interestCalculator;

    @Autowired
    public DebtController(DebtService debtService, InterestCalculator interestCalculator) {
        this.debtService = debtService;
        this.interestCalculator = interestCalculator;
    }

    //tested
    @GetMapping("/{id}")
    public ResponseEntity<?> getDebtById(@PathVariable("id") int id, @RequestParam int userId) {
        try {
            Debt debt = debtService.getDebtById(id, userId);
            if (debt == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Debt with id " + id + " not found");
            }
            return ResponseEntity.ok().body(debt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
        }
    }


    @GetMapping("/{id}/interest-rate")
    public ResponseEntity<?> getInterestRate(@PathVariable("id") int id, @RequestParam int userId) {
        try {
            Debt debt = debtService.getDebtById(id, userId);
            if (debt == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Debt with id " + id + " not found");
            }
            LocalDate from = debt.getFromDate();
            LocalDate due = debt.getDueDate();
            int interestRate = debt.getInterestRate();
            long daysBetween = ChronoUnit.DAYS.between(from, due);
            float interest = interestCalculator.calculateInterest(debt.getAmount(), daysBetween, interestRate);
            return ResponseEntity.ok().body(interest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
        }
    }

    @PostMapping("/{id}/set-calculation-strategy")
    public void setInterestCalculationStrategy(@RequestParam int userId, @RequestParam int numberOfStrategy) {
        switch (numberOfStrategy){
            case 1:
                interestCalculator.setStrategy(new CompoundInterestCalculationStrategy());
                break;
            case 2:
                interestCalculator.setStrategy(new SimpleInterestCalculationStrategy());
                break;
        }
    }


    //tested
    @PostMapping("/add-debt")
    public ResponseEntity<?> addDebt(@RequestBody DebtDTO debtDto,  @RequestParam int userId) {

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        if (debtDto.getDueDate().isBefore(currentDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debt can not be before the current date");
        }
        Debt debt = new Debt();
        debt.setFromDate(currentDate);
        debt.setUserId(userId);
        debt.setAmount(debtDto.getAmount());
        debt.setName(debtDto.getName());
        debt.setNameOfPersonToGiveBack(debtDto.getNameOfPersonToGiveBack());
        debt.setDueDate(debtDto.getDueDate());
        debt.setInterestRate(debtDto.getInterestRate());
        debtService.createDebt(debt);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    //tested
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDebt(@PathVariable("id") int id, @RequestBody Debt updatedDebt,  @RequestParam int userId) {
        if (!debtRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Debt with id " + id + " not found.");
        }
        updatedDebt.setId(id);
        debtService.updateDebt(updatedDebt, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDebt(@PathVariable("id") int id, @RequestParam int userId) {
        if (!debtRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Debt with id " + id + " not found");
        }
        debtService.deleteDebt(id, userId);
        return ResponseEntity.noContent().build();
    }


//    @PostMapping("/add-memento")
//    public ResponseEntity<?> addDebtMemento(@RequestBody ) {
//        //debtService.addDebtMemento(debtMementoDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
}
