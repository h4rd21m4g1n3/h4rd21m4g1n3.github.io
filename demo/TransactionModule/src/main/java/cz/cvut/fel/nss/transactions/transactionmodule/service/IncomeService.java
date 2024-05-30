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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class IncomeService  {

    private final IncomeRepository incomeRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;

    //@Autowired
    //private ElasticsearchRestTemplate elasticsearchTemplate;

//    public IncomeService(IncomeRepository incomeRepository, IncomeCategoryRepository incomeCategoryRepository
//            , TransactionSearchRepository transactionSearchRepository) {
//        this.incomeRepository = incomeRepository;
//        this.incomeCategoryRepository = incomeCategoryRepository;
//        this.transactionSearchRepository = transactionSearchRepository;
//    }
public IncomeService(IncomeRepository incomeRepository, IncomeCategoryRepository incomeCategoryRepository) {
    this.incomeRepository = incomeRepository;
    this.incomeCategoryRepository = incomeCategoryRepository;
}


    public void addIncomeCategory(String categoryName) {
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setCategoryName(categoryName);
        incomeCategoryRepository.save(incomeCategory);
    }
    public Income getIncomeById(int incomeId, int userId) {
        Optional<Income> optionalIncome = incomeRepository.findByIdAndUserId(incomeId, userId);
        return optionalIncome.orElseThrow(() -> new RuntimeException("Income not found with id: " + incomeId + " for user: " + userId));
    }

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

    @Transactional
    public void deleteIncome(int incomeId, int userId) {
        getIncomeById(incomeId, userId);
        incomeRepository.deleteById(incomeId);
    }

    @Transactional(readOnly = true)
    public List<Income> getIncomesByIncomeCategoryAndUserId(IncomeCategory incomeCategory, int userId) {
        return incomeRepository.findByIncomeCategoryAndUserId(incomeCategory, userId);
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

    public List<Income> getAllExpensesDescendingOrder(int userId) {
        return incomeRepository.findAllByOrderByTransactionDateDesc(userId);
    }

    public List<Income> getAllExpensesAscendingOrder(int userId) {
        return incomeRepository.findAllByOrderByTransactionDateAsc(userId);
    }

    public List<Income> filterIncomesByAmountRange(int userId, float fromAmount, float toAmount) {
        return incomeRepository.findByAmountBetweenOrderByAmountAsc(userId, fromAmount, toAmount);
    }

    public List<Income> filterIncomesByAmountStartingFrom(int userId, float fromAmount) {
        return incomeRepository.findByAmountGreaterThanEqualOrderByAmountAsc(userId, fromAmount);
    }

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
