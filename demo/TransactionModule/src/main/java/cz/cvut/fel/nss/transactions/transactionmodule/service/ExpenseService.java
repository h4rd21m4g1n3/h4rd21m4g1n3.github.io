package cz.cvut.fel.nss.transactions.transactionmodule.service;



import cz.cvut.fel.nss.transactions.transactionmodule.entity.*;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.ExpenseCategoryRepository;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ExpenseService  {
    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }


    public Expense getExpenseById(int expenseId) {
        return expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + expenseId));
    }

    @Transactional
    public void updateExpense(Expense updatedExpense) {
        Objects.requireNonNull(updatedExpense);
        Expense existingExpense = getExpenseById(updatedExpense.getId());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setName(updatedExpense.getName());
        existingExpense.setTransactionDate(updatedExpense.getTransactionDate());
        existingExpense.setExpenseCategory(updatedExpense.getExpenseCategory());
        expenseRepository.save(existingExpense);
    }

    @Transactional
    public void createExpense(Expense expense){
        Objects.requireNonNull(expense);
        expenseRepository.save(expense);
    }

    @Transactional
    public void deleteExpense(int expenseId) {
        getExpenseById(expenseId);
        expenseRepository.deleteById(expenseId);
    }

    @Transactional(readOnly = true)
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void addExpenseCategory(String categoryName) {
        // Create a new IncomeCategory
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setCategoryName(categoryName);
        expenseCategoryRepository.save(expenseCategory);
    }

    @Transactional(readOnly = true)
    public List<ExpenseCategory> getAllExpenseCategories() {
        return expenseCategoryRepository.findAll();
    }

    public List<Expense> getExpensesByExpenseCategory(ExpenseCategory expenseCategory) {
        return expenseRepository.findByExpenseCategory(expenseCategory);
    }

    public List<Expense> getAllExpensesDescendingOrder() {
        return expenseRepository.findAllByOrderByTransactionDateDesc();
    }

    public List<Expense> getAllExpensesAscendingOrder() {
        return expenseRepository.findAllByOrderByTransactionDateAsc();
    }

    public List<Expense> filterExpensesByAmountRange(float fromAmount, float toAmount) {
        return expenseRepository.findByAmountBetweenOrderByAmountAsc(fromAmount, toAmount);
    }
}
