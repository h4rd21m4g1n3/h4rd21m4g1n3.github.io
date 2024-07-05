package cz.cvut.fel.nss.transactions.transaction_module.service;



import cz.cvut.fel.nss.transactions.transaction_module.entity.*;
import cz.cvut.fel.nss.transactions.transaction_module.repository.ExpenseCategoryRepository;
import cz.cvut.fel.nss.transactions.transaction_module.repository.ExpenseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class for managing expenses and expense categories.
 */
@Service
@Transactional
public class ExpenseService  {
    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;


    public ExpenseService(ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    /**
     * Retrieves an expense by its ID and user ID.
     *
     * @param expenseId the ID of the expense
     * @param userId the ID of the user
     * @return the expense with the given ID and user ID
     * @throws RuntimeException if the expense is not found
     */
    public Expense getExpenseById(int expenseId, int userId) {
        Optional<Expense> optionalExpense = expenseRepository.findByIdAndUserId(expenseId, userId);
        return optionalExpense.orElseThrow(() -> new RuntimeException("Expense not found with id: " + expenseId + " for user: " + userId));
    }

    /**
     * Updates an existing expense.
     *
     * @param updatedExpense the updated expense data
     * @param userId the ID of the user
     */
    @Transactional
    public void updateExpense(Expense updatedExpense, int userId) {
        Objects.requireNonNull(updatedExpense);
        Expense existingExpense = getExpenseById(updatedExpense.getId(), userId);
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setName(updatedExpense.getName());
        existingExpense.setTransactionDate(updatedExpense.getTransactionDate());
        existingExpense.setExpenseCategory(updatedExpense.getExpenseCategory());
        expenseRepository.save(existingExpense);
    }

    /**
     * Creates a new expense.
     *
     * @param expense the expense to be created
     */
    @Transactional
    public void createExpense(Expense expense){
        Objects.requireNonNull(expense);
        expenseRepository.save(expense);
    }

    /**
     * Deletes an expense by its ID and user ID.
     *
     * @param expenseId the ID of the expense
     * @param userId the ID of the user
     */
    @Transactional
    public void deleteExpense(int expenseId, int userId) {
        getExpenseById(expenseId, userId);
        expenseRepository.deleteById(expenseId);
    }

    /**
     * Retrieves all expenses.
     *
     * @return a list of all expenses
     */
    @Transactional(readOnly = true)
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    /**
     * Adds a new expense category.
     *
     * @param categoryName the name of the new expense category
     */
    public void addExpenseCategory(String categoryName) {
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setCategoryName(categoryName);
        expenseCategoryRepository.save(expenseCategory);
    }


    /**
     * Retrieves all expense categories.
     *
     * @return a list of all expense categories
     */
    @Transactional(readOnly = true)
    public List<ExpenseCategory> getAllExpenseCategories() {
        return expenseCategoryRepository.findAll();
    }

    /**
     * Retrieves expenses by expense category and user ID.
     *
     * @param expenseCategory the expense category
     * @param userId the ID of the user
     * @return a list of expenses in the specified category for the specified user
     */
    public List<Expense> getExpensesByExpenseCategory(ExpenseCategory expenseCategory, int userId) {
        return expenseRepository.findByExpenseCategoryAndUserId(expenseCategory, userId);
    }

    /**
     * Retrieves all expenses in descending order by transaction date for a user.
     *
     * @param userId the ID of the user
     * @return a list of expenses in descending order by transaction date
     */
    public List<Expense> getAllExpensesDescendingOrder(int userId) {
        return expenseRepository.findAllByOrderByTransactionDateDesc(userId);
    }

    /**
     * Retrieves all expenses in ascending order by transaction date for a user.
     *
     * @param userId the ID of the user
     * @return a list of expenses in ascending order by transaction date
     */
    public List<Expense> getAllExpensesAscendingOrder(int userId) {
        return expenseRepository.findAllByOrderByTransactionDateAsc(userId);
    }

    /**
     * Filters expenses by amount range for a user.
     *
     * @param userId the ID of the user
     * @param fromAmount the minimum amount
     * @param toAmount the maximum amount
     * @return a list of expenses within the specified amount range
     */
    public List<Expense> filterExpensesByAmountRange(int userId, float fromAmount, float toAmount) {
        return expenseRepository.findByAmountBetweenOrderByAmountAsc(userId, fromAmount, toAmount);
    }

    /**
     * Retrieves expenses by expense category and user ID.
     *
     * @param expenseCategory the expense category
     * @param userId the ID of the user
     * @return a list of expenses in the specified category for the specified user
     */
    @Transactional(readOnly = true)
    public List<Expense> getExpensesByExpenseCategoryAndUserId(ExpenseCategory expenseCategory, int userId) {
        return expenseRepository.findByExpenseCategoryAndUserId(expenseCategory, userId);
    }

    /**
     * Filters expenses by amount starting from a specified value for a user.
     *
     * @param userId the ID of the user
     * @param fromAmount the minimum amount
     * @return a list of expenses with amount greater than or equal to the specified value
     */
    public List<Expense> filterExpensesByAmountStartingFrom(int userId, float fromAmount) {
        return expenseRepository.findByAmountGreaterThanEqualOrderByAmountAsc(userId, fromAmount);
    }

}
