package cz.cvut.fel.nss.transactions.transactionmodule.service;


import cz.cvut.fel.nss.transactions.transactionmodule.entity.Expense;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Income;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.IncomeCategory;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Transaction;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.IncomeCategoryRepository;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.IncomeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class IncomeService  {

    private final IncomeRepository incomeRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;

    public IncomeService(IncomeRepository incomeRepository, IncomeCategoryRepository incomeCategoryRepository) {
        this.incomeRepository = incomeRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
    }


    public void addIncomeCategory(String categoryName) {
        // Create a new IncomeCategory
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setCategoryName(categoryName);
        incomeCategoryRepository.save(incomeCategory);
    }
    public Income getIncomeById(int incomeId) {
        return incomeRepository.findById(incomeId)
                .orElseThrow(() -> new RuntimeException("Income not found with id: " + incomeId));
    }

    @Transactional
    public void updateIncome(Income updatedIncome) {
        Objects.requireNonNull(updatedIncome);
        Income existingIncome = getIncomeById(updatedIncome.getId());
        existingIncome.setAmount(updatedIncome.getAmount());
        existingIncome.setName(updatedIncome.getName());
        existingIncome.setTransactionDate(updatedIncome.getTransactionDate());
        existingIncome.setIncomeCategory(updatedIncome.getIncomeCategory());
        incomeRepository.save(existingIncome);
    }
    @Transactional
    public void createIncome(Income income){
        Objects.requireNonNull(income);
        incomeRepository.save(income);
    }

    @Transactional
    public void deleteIncome(int incomeId) {
        getIncomeById(incomeId);
        incomeRepository.deleteById(incomeId);
    }

    @Transactional(readOnly = true)
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<IncomeCategory> getAllIncomeCategories() {
        return incomeCategoryRepository.findAll();
    }

    public List<Income> getIncomesByIncomeCategory(IncomeCategory incomeCategory) {
        return incomeRepository.findByIncomeCategory(incomeCategory);
    }

    public List<Income> getAllExpensesDescendingOrder() {
        return incomeRepository.findAllByOrderByTransactionDateDesc();
    }

    public List<Income> getAllExpensesAscendingOrder() {
        return incomeRepository.findAllByOrderByTransactionDateAsc();
    }

    public List<Income> filterIncomesByAmountRange(float fromAmount, float toAmount) {
        return incomeRepository.findByAmountBetweenOrderByAmountAsc(fromAmount, toAmount);
    }

    public List<Income> filterIncomesByAmountStartingFrom(float fromAmount) {
        return incomeRepository.findByAmountGreaterThanEqualOrderByAmountAsc(fromAmount);
    }
}
