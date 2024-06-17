package cz.cvut.fel.nss.transactions.transactionmodule.service;

import cz.cvut.fel.nss.transactions.transactionmodule.entity.Transaction;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.elasticentity.TransactionDocument;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;

//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchTemplate;
//    private final TransactionSearchRepository transactionSearchRepository;


    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;

    }

    /**
     * Retrieves a transaction by its ID and user ID.
     *
     * @param transactionId the ID of the transaction
     * @param userId the ID of the user
     * @return the transaction with the given ID and user ID
     * @throws RuntimeException if the transaction is not found
     */
    public Transaction getTransactionById(int transactionId, int userId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findByIdAndUserId(transactionId, userId);
        return optionalTransaction.orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId + " for user: " + userId));
    }


    /**
     * Creates a new transaction.
     *
     * @param transaction the transaction to be created
     */
    @Transactional
    public void createTransaction(Transaction transaction){
        Objects.requireNonNull(transaction);
        try {
            if (transactionRepository.existsById(transaction.getId())) {
                throw new IllegalArgumentException("Transaction with ID " + transaction.getId() + " already exists.");
            } else {
                transactionRepository.save(transaction);

                //elastic search doesnt work :- (
                //TransactionDocument transactionDocument = convertToTransactionDocument(transaction);
                //transactionSearchRepository.save(transactionDocument);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a Transaction object to a TransactionDocument object.
     *
     * @param transaction the transaction to be converted
     * @return the corresponding TransactionDocument object
     */
    private TransactionDocument convertToTransactionDocument(Transaction transaction) {
        TransactionDocument transactionDocument = new TransactionDocument();
        transactionDocument.setId(String.valueOf(transaction.getId()));
        transactionDocument.setUserId(transaction.getUserId());
        transactionDocument.setAmount(transaction.getAmount());
        transactionDocument.setName(transaction.getName());
        transactionDocument.setTransactionDate(transaction.getTransactionDate());
        return transactionDocument;
    }

    /**
     * Retrieves all transactions in descending order by transaction date for a user.
     *
     * @param userId the ID of the user
     * @return a list of transactions in descending order by transaction date
     */
    public List<Transaction> getAllExpensesDescendingOrder(int userId) {
        return transactionRepository.findAllByOrderByTransactionDateDesc(userId);
    }

    /**
     * Retrieves all transactions in ascending order by transaction date for a user.
     *
     * @param userId the ID of the user
     * @return a list of transactions in ascending order by transaction date
     */
    public List<Transaction> getAllExpensesAscendingOrder(int userId) {
        return transactionRepository.findAllByOrderByTransactionDateAsc(userId);
    }

    /**
     * Updates an existing transaction.
     *
     * @param updatedTransaction the updated transaction data
     * @param userId the ID of the user
     * @return the updated transaction
     */
    @Transactional
    public Transaction updateTransaction(Transaction updatedTransaction, int userId) {
        Objects.requireNonNull(updatedTransaction);
        Transaction existingTransaction = getTransactionById(updatedTransaction.getId(), userId);
        transactionRepository.save(existingTransaction);

        //elastic search doesnt work :- (
        //TransactionDocument transactionDocument = convertToTransactionDocument(existingTransaction);
        //transactionSearchRepository.save(transactionDocument);

        return existingTransaction;
    }

    /**
     * Deletes a transaction by its ID and user ID.
     *
     * @param transactionId the ID of the transaction
     * @param userId the ID of the user
     */
    @Transactional
    public void deleteTransaction(int transactionId, int userId) {
        getTransactionById(transactionId, userId);
        transactionRepository.deleteById(transactionId);
    }


    /**
     * Retrieves all transactions for a user.
     *
     * @param userId the ID of the user
     * @return a list of all transactions for the user
     */
    public List<Transaction> getAllTransactions(int userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    /**
     * Filters transactions by amount range.
     *
     * @param fromAmount the minimum amount
     * @param toAmount the maximum amount
     * @return a list of transactions within the specified amount range
     */
    public List<Transaction> filterTransactionsByAmountRange(float fromAmount, float toAmount) {
        return transactionRepository.findByAmountBetweenOrderByAmountAsc(fromAmount, toAmount);
    }


}
