package cz.cvut.fel.nss.transactions.financemodule.controller;


import cz.cvut.fel.nss.transactions.financemodule.dto.GoalDTO;
import cz.cvut.fel.nss.transactions.financemodule.entity.Goal;
import cz.cvut.fel.nss.transactions.financemodule.entity.GoalMementoEntity;
import cz.cvut.fel.nss.transactions.financemodule.kafka.dto.TransactionInfoDto;
import cz.cvut.fel.nss.transactions.financemodule.repository.GoalRepository;
import cz.cvut.fel.nss.transactions.financemodule.service.GoalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/finances/goals")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }


    @Autowired
    private GoalRepository goalRepository;

    //tested
    @GetMapping("/{id}")
    @Cacheable(value = "goals", key = "#id")
    public ResponseEntity<?> getGoalById(@PathVariable("id") int id, @RequestParam int userId) {
        try {
            Goal goal = goalService.getGoalById(id, userId);
            if (goal == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goal with id " + id + " not found");
            }
            return ResponseEntity.ok().body(goal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
        }
    }


    //tested
    @PostMapping("/add-goal")
    public ResponseEntity<?> addGoal(@RequestBody GoalDTO goalDto, @RequestParam int userId) {
        Goal goal = new Goal();
        goal.setAmount(goalDto.getAmount());
        goal.setName(goalDto.getName());
        goal.setUserId(userId);

        goalService.createGoal(goal);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //tested
    @PutMapping("/{id}")
    @CacheEvict(value = "goals", key = "#id")
    public ResponseEntity<?> updateGoal(@PathVariable("id") int id, @RequestBody Goal updatedGoal,  @RequestParam int userId) {
        if (!goalRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goal with id " + id + " not found.");
        }
        updatedGoal.setId(id);
        goalService.updateGoal(updatedGoal, userId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    @CacheEvict(value = "goals", key = "#id")
    public ResponseEntity<?> deleteGoal(@PathVariable("id") int id, @RequestParam int userId) {
        if (!goalRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goal with id " + id + " not found");
        }
        goalService.deleteGoal(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<GoalMementoEntity>> getGoalHistory(@PathVariable("id") int goalId) {
        try {
            List<GoalMementoEntity> goalHistory = goalService.getGoalHistory(goalId);
            if (goalHistory.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(goalHistory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Goal>> getAllGoals(@RequestParam int userId) {
        List<Goal> goals = goalService.getAllGoals(userId);
        return ResponseEntity.ok().body(goals);
    }

    @PostMapping("/update-with-transaction")
    public ResponseEntity<String> updateGoalWithTransaction(
            @RequestBody TransactionInfoDto transactionInfo) {
        try {
            goalService.updateGoalWithTransaction(transactionInfo);
            return ResponseEntity.ok("Goals updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update goals: " + e.getMessage());
        }
    }

}
