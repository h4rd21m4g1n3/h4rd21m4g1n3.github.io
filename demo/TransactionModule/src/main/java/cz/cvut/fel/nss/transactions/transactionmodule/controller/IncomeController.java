package cz.cvut.fel.nss.transactions.transactionmodule.controller;
import cz.cvut.fel.nss.transactions.transactionmodule.dto.IncomeDTO;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Expense;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Income;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.IncomeCategory;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.elasticentity.TransactionDocument;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.IncomeCategoryRepository;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.IncomeRepository;

import cz.cvut.fel.nss.transactions.transactionmodule.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/transactions/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    @Autowired
    private IncomeRepository incomeRepository;




    @PostMapping("/add-income")
    public ResponseEntity<?> addIncome(@RequestBody IncomeDTO incomeDto, @RequestParam int userId) {
        IncomeCategory incomeCategory = incomeDto.getIncomeCategory();
        Long incomeCategoryId = incomeDto.getIncomeCategory().getId();

        Optional<IncomeCategory> optionalIncomeCategory = incomeCategoryRepository.findById(Math.toIntExact(incomeCategoryId));

        // Check if the IncomeCategory exists and if its categoryName matches
        if (optionalIncomeCategory.isPresent()) {
            IncomeCategory retrievedCategory = optionalIncomeCategory.get();

            if (!retrievedCategory.getCategoryName().equals(incomeCategory.getCategoryName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category name for the given id");
            }

        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid incomeCategory id*");
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        if (incomeDto.getTransactionDate().isAfter(currentDate) || incomeDto.getTransactionDate().isBefore(startDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction date must be between 2000 and the current date");
        }

        Income income = new Income();
        income.setUserId(userId);
        income.setAmount(incomeDto.getAmount());
        income.setName(incomeDto.getName());
        income.setTransactionDate(incomeDto.getTransactionDate());
        income.setIncomeCategory(incomeCategory);

        incomeService.createIncome(income);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIncome(@PathVariable("id") int id, @RequestBody Income updatedIncome,  @RequestParam int userId) {
        if (!incomeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income with id " + id + " not found");
        }
        updatedIncome.setId(id);
        incomeService.updateIncome(updatedIncome, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable("id") int id, @RequestParam int userId) {
        if (!incomeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income with id " + id + " not found");
        }
        incomeService.deleteIncome(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIncomeById(@PathVariable("id") int id, @RequestParam int userId) {
        try {
            Income income = incomeService.getIncomeById(id, userId);
            if (income == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income with id " + id + " not found");
            }
            return ResponseEntity.ok().body(income);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
        }
    }

    /////////////////////////////////////////////////////////////


    //tested
    @GetMapping("/all_incomes_desc")
    public ResponseEntity<List<Income>> getAllIncomesDesc(@RequestParam int userId) {
        List<Income> incomes = incomeService.getAllExpensesDescendingOrder(userId);
        return ResponseEntity.ok().body(incomes);
    }

    //tested
    @GetMapping("/all_incomes_asc")
    public ResponseEntity<List<Income>> getAllIncomesAsc(@RequestParam int userId) {
        List<Income> incomes = incomeService.getAllExpensesAscendingOrder(userId);
        return ResponseEntity.ok().body(incomes);
    }

    @PostMapping("/add-category")
    public ResponseEntity<?> addIncomeCategory(@RequestBody Map<String, String> requestBody) {
        String categoryName = requestBody.get("categoryName");
        incomeService.addIncomeCategory(categoryName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<IncomeCategory>> getAllIncomeCategories() {
        List<IncomeCategory> categories = incomeService.getAllIncomeCategories();
        return ResponseEntity.ok().body(categories);
    }


    @GetMapping("/incomes-by-category/{categoryId}")
    public ResponseEntity<List<Income>> getIncomesByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam int userId) {

        Optional<IncomeCategory> incomeCategory = incomeCategoryRepository.findById(Math.toIntExact(categoryId));

        if (!incomeCategory.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<Income> incomes = incomeService.getIncomesByIncomeCategoryAndUserId(incomeCategory.get(), userId);
        return ResponseEntity.ok(incomes);
    }


    //tested
    @GetMapping("/filter-by-amount")
    public ResponseEntity<List<Income>> filterIncomesByAmountRange(
            @RequestParam("from") float fromAmount,
            @RequestParam(value = "to", required = false) Float toAmount,
            @RequestParam int userId) {

        if (toAmount != null && fromAmount > toAmount) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        List<Income> filteredIncomes;
        if (toAmount != null) {
            filteredIncomes = incomeService.filterIncomesByAmountRange(userId, fromAmount, toAmount);
        } else {
            filteredIncomes = incomeService.filterIncomesByAmountStartingFrom(userId, fromAmount);
        }

        return ResponseEntity.ok().body(filteredIncomes);
    }

//    @GetMapping("/search")
//    public List<TransactionDocument> searchTransactions(@RequestParam String searchTerm) {
//        return incomeService.searchTransactions(searchTerm);
//    }


}
