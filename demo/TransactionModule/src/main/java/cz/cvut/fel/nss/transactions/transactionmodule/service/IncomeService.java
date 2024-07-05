package cz.cvut.fel.nss.transactions.transactionmodule.service;


import cz.cvut.fel.nss.transactions.transactionmodule.entity.Expense;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Income;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.IncomeCategory;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Transaction;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.elasticentity.TransactionDocument;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.IncomeCategoryRepository;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.IncomeRepository;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHits;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class for managing incomes and income categories.
 */
@Service
@Transactional
public class IncomeService  {

    private final IncomeRepository incomeRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;

    public IncomeService(IncomeRepository incomeRepository, IncomeCategoryRepository incomeCategoryRepository) {
        this.incomeRepository = incomeRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    /**
     * Adds a new income category.
     *
     * @param categoryName the name of the new income category
     */
    public void addIncomeCategory(String categoryName) {
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setCategoryName(categoryName);
        incomeCategoryRepository.save(incomeCategory);
    }

    /**
     * Retrieves an income by its ID and user ID.
     *
     * @param incomeId the ID of the income
     * @param userId the ID of the user
     * @return the income with the given ID and user ID
     * @throws RuntimeException if the income is not found
     */
    public Income getIncomeById(int incomeId, int userId) {
        Optional<Income> optionalIncome = incomeRepository.findByIdAndUserId(incomeId, userId);
        return optionalIncome.orElseThrow(() -> new RuntimeException("Income not found with id: " + incomeId + " for user: " + userId));
    }


    /**
     * Updates an existing income.
     *
     * @param updatedIncome the updated income data
     * @param userId the ID of the user
     */
    @Transactional
    public void updateIncome(Income updatedIncome, int userId) {
        Objects.requireNonNull(updatedIncome);
        Income existingIncome = getIncomeById(updatedIncome.getId(), userId);
        existingIncome.setAmount(updatedIncome.getAmount());
        existingIncome.setName(updatedIncome.getName());
        existingIncome.setTransactionDate(updatedIncome.getTransactionDate());
        existingIncome.setIncomeCategory(updatedIncome.getIncomeCategory());
        incomeRepository.save(existingIncome);
    }

    /**
     * Creates a new income.
     *
     * @param income the income to be created
     */
    @Transactional
    public void createIncome(Income income){
        Objects.requireNonNull(income);
        incomeRepository.save(income);
        //TransactionDocument transactionDocument = convertToTransactionDocument(income);
        //transactionSearchRepository.save(transactionDocument);
    }

    private TransactionDocument convertToTransactionDocument(Income income) {
        TransactionDocument transactionDocument = new TransactionDocument();
        transactionDocument.setId(String.valueOf(income.getId()));
        transactionDocument.setUserId(income.getUserId());
        transactionDocument.setAmount(income.getAmount());
        transactionDocument.setName(income.getName());
        transactionDocument.setTransactionDate(income.getTransactionDate());
        return transactionDocument;
    }

    /**
     * Deletes an income by its ID and user ID.
     *
     * @param incomeId the ID of the income
     * @param userId the ID of the user
     */
    @Transactional
    public void deleteIncome(int incomeId, int userId) {
        getIncomeById(incomeId, userId);
        incomeRepository.deleteById(incomeId);
    }

    @Transactional(readOnly = true)
    public List<Income> getIncomesByIncomeCategoryAndUserId(IncomeCategory incomeCategory, int userId) {
        return incomeRepository.findByIncomeCategoryAndUserId(incomeCategory, userId);
    }

    /**
     * Retrieves all incomes.
     *
     * @return a list of all incomes
     */
    @Transactional(readOnly = true)
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    /**
     * Retrieves all income categories.
     *
     * @return a list of all income categories
     */
    @Transactional(readOnly = true)
    public List<IncomeCategory> getAllIncomeCategories() {
        return incomeCategoryRepository.findAll();
    }

    /**
     * Retrieves incomes by income category.
     *
     * @param incomeCategory the income category
     * @return a list of incomes in the specified category
     */
    public List<Income> getIncomesByIncomeCategory(IncomeCategory incomeCategory) {
        return incomeRepository.findByIncomeCategory(incomeCategory);
    }

    /**
     * Retrieves all incomes in descending order by transaction date for a user.
     *
     * @param userId the ID of the user
     * @return a list of incomes in descending order by transaction date
     */
    public List<Income> getAllExpensesDescendingOrder(int userId) {
        return incomeRepository.findAllByOrderByTransactionDateDesc(userId);
    }

    /**
     * Retrieves all incomes in ascending order by transaction date for a user.
     *
     * @param userId the ID of the user
     * @return a list of incomes in ascending order by transaction date
     */
    public List<Income> getAllExpensesAscendingOrder(int userId) {
        return incomeRepository.findAllByOrderByTransactionDateAsc(userId);
    }

    /**
     * Filters incomes by amount range for a user.
     *
     * @param userId the ID of the user
     * @param fromAmount the minimum amount
     * @param toAmount the maximum amount
     * @return a list of incomes within the specified amount range
     */
    public List<Income> filterIncomesByAmountRange(int userId, float fromAmount, float toAmount) {
        return incomeRepository.findByAmountBetweenOrderByAmountAsc(userId, fromAmount, toAmount);
    }

    /**
     * Filters incomes by amount starting from a specified value for a user.
     *
     * @param userId the ID of the user
     * @param fromAmount the minimum amount
     * @return a list of incomes with amount greater than or equal to the specified value
     */
    public List<Income> filterIncomesByAmountStartingFrom(int userId, float fromAmount) {
        return incomeRepository.findByAmountGreaterThanEqualOrderByAmountAsc(userId, fromAmount);
    }

    /**
     * WE TRIED TO MAKE ELASTIC SEARCH FOR 100 HOURS BUT IT DOESNT WORK :- (
     */
    // Modify searchTransactions method to perform a fuzzy search on the name field
//    public List<TransactionDocument> searchTransactions(String searchTerm) {
//        Query searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchQuery("name", searchTerm)
//                        .fuzziness("AUTO")) // Fuzzy query on the name field
//                .build();
//
//        SearchHits<TransactionDocument> searchHits = elasticsearchTemplate.search(searchQuery, TransactionDocument.class);
//
//        List<TransactionDocument> transactions = new ArrayList<>();
//        searchHits.forEach(searchHit -> transactions.add(searchHit.getContent()));
//
//        return transactions;
//    }
}
