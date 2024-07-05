package cz.cvut.fel.nss.transactions.transactionmodule.controller;

import cz.cvut.fel.nss.transactions.transactionmodule.dto.ExpenseDTO;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Expense;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.ExpenseCategory;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Income;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.IncomeCategory;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.ExpenseCategoryRepository;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.ExpenseRepository;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.IncomeCategoryRepository;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.IncomeRepository;
import cz.cvut.fel.nss.transactions.transactionmodule.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * REST controller for managing expenses.
 */
@RestController
@RequestMapping("/transactions/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    /**
     * Adds a new expense.
     *
     * @param expenseDto the expense data transfer object
     * @param userId the ID of the user
     * @return a response indicating the status of the request
     */
    @PostMapping("/add-expense")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseDTO expenseDto, @RequestParam int userId) {
        ExpenseCategory expenseCategory = expenseDto.getExpenseCategory();
        Long expenseCategoryId = expenseDto.getExpenseCategory().getId();

        Optional<ExpenseCategory> optionalExpenseCategory = expenseCategoryRepository.findById(Math.toIntExact(expenseCategoryId));


        if (optionalExpenseCategory.isPresent()) {
            ExpenseCategory retrievedCategory = optionalExpenseCategory.get();

            if (!retrievedCategory.getCategoryName().equals(expenseCategory.getCategoryName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category name for the given id");
            }

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid expenseCategory id");
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        if (expenseDto.getTransactionDate().isAfter(currentDate) || expenseDto.getTransactionDate().isBefore(startDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction date must be between 2000 and the current date");
        }

        Expense expense = new Expense();
        expense.setUserId(userId);  // Set the userId for the transaction
        expense.setAmount(expenseDto.getAmount());
        expense.setName(expenseDto.getName());
        expense.setTransactionDate(expenseDto.getTransactionDate());
        expense.setExpenseCategory(expenseCategory);

        expenseService.createExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * Updates an existing expense.
     *
     * @param id the ID of the expense
     * @param updatedExpense the updated expense data
     * @param userId the ID of the user
     * @return a response indicating the status of the request
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable("id") int id, @RequestBody Expense updatedExpense,  @RequestParam int userId) {
        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense with id " + id + " not found");
        }
        updatedExpense.setId(id);
        expenseService.updateExpense(updatedExpense, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes an existing expense.
     *
     * @param id the ID of the expense
     * @param userId the ID of the user
     * @return a response indicating the status of the request
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable("id") int id,  @RequestParam int userId) {
        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income with id " + id + " not found");
        }
        expenseService.deleteExpense(id, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves an expense by its ID and user ID.
     *
     * @param id the ID of the expense
     * @param userId the ID of the user
     * @return the expense with the given ID and user ID, or an error response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable("id") int id, @RequestParam int userId) {
        try {
            Expense expense = expenseService.getExpenseById(id, userId);
            if (expense == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense with id " + id + " not found");
            }
            return ResponseEntity.ok().body(expense);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
        }
    }

    //////////////////////

    /**
     * Retrieves all expenses in descending order by transaction date for a user.
     *
     * @param userId the ID of the user
     * @return a list of expenses in descending order by transaction date
     */
    @GetMapping("/all_expenses_desc")
    public ResponseEntity<List<Expense>> getAllExpensesDesc(@RequestParam int userId) {
        List<Expense> expenses = expenseService.getAllExpensesDescendingOrder(userId);
        return ResponseEntity.ok().body(expenses);
    }

    /**
     * Retrieves all expenses in ascending order by transaction date for a user.
     *
     * @param userId the ID of the user
     * @return a list of expenses in ascending order by transaction date
     */
    @GetMapping("/all_expenses_asc")
    public ResponseEntity<List<Expense>> getAllExpensesAsc(@RequestParam int userId) {
        List<Expense> expenses = expenseService.getAllExpensesAscendingOrder(userId);
        return ResponseEntity.ok().body(expenses);
    }

    /**
     * Adds a new expense category.
     *
     * @param requestBody a map containing the category name
     * @return a response indicating the status of the request
     */
    @PostMapping("/add-category")
    public ResponseEntity<?> addExpenseCategory(@RequestBody Map<String, String> requestBody) {
        String categoryName = requestBody.get("categoryName");
        expenseService.addExpenseCategory(categoryName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Retrieves all expense categories.
     *
     * @return a list of all expense categories
     */
    @GetMapping("/categories")
    public ResponseEntity<List<ExpenseCategory>> getAllExpenseCategories() {
        List<ExpenseCategory> categories = expenseService.getAllExpenseCategories();
        return ResponseEntity.ok().body(categories);
    }

    /**
     * Retrieves expenses by category ID and user ID.
     *
     * @param categoryId the ID of the expense category
     * @param userId the ID of the user
     * @return a list of expenses in the specified category for the specified user
     */
    @GetMapping("/expenses-by-category/{categoryId}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam int userId) {

        Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(Math.toIntExact(categoryId));

        if (!expenseCategory.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<Expense> expenses = expenseService.getExpensesByExpenseCategory(expenseCategory.get(), userId);
        return ResponseEntity.ok(expenses);
    }


    /**
     * Filters expenses by amount range for a user.
     *
     * @param fromAmount the minimum amount
     * @param toAmount the maximum amount (optional)
     * @param userId the ID of the user
     * @return a list of expenses within the specified amount range
     */
    @GetMapping("/filter-by-amount")
    public ResponseEntity<List<Expense>> filterExpensesByAmountRange(
            @RequestParam("from") float fromAmount,
            @RequestParam(value = "to", required = false) Float toAmount,
            @RequestParam int userId) {

        if (toAmount != null && fromAmount > toAmount) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        List<Expense> filteredExpenses;
        if (toAmount != null) {
            filteredExpenses = expenseService.filterExpensesByAmountRange(userId, fromAmount, toAmount);
        } else {
            filteredExpenses = expenseService.filterExpensesByAmountStartingFrom(userId, fromAmount);
        }

        return ResponseEntity.ok().body(filteredExpenses);
    }
}