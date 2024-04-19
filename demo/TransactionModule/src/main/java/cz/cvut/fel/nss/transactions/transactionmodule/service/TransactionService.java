package cz.cvut.fel.nss.transactions.transactionmodule.service;

import cz.cvut.fel.nss.transactions.transactionmodule.entity.Expense;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Transaction;
import cz.cvut.fel.nss.transactions.transactionmodule.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public Transaction getTransactionById(int transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));
    }


    @Transactional
    public void createTransaction(Transaction transaction){
        Objects.requireNonNull(transaction);
        try {
            if (transactionRepository.existsById(transaction.getId())) {
                throw new IllegalArgumentException("Transaction with ID " + transaction.getId() + " already exists.");
            } else {
                transactionRepository.save(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Transaction> getAllExpensesDescendingOrder() {
        return transactionRepository.findAllByOrderByTransactionDateDesc();
    }

    public List<Transaction> getAllExpensesAscendingOrder() {
        return transactionRepository.findAllByOrderByTransactionDateAsc();
    }

    @Transactional
    public Transaction updateTransaction(Transaction updatedTransaction) {
        Objects.requireNonNull(updatedTransaction);
        Transaction existingTransaction = getTransactionById(updatedTransaction.getId());
        return transactionRepository.save(existingTransaction);
    }

    @Transactional
    public void deleteTransaction(int transactionId) {
        getTransactionById(transactionId);
        transactionRepository.deleteById(transactionId);
    }

    @Transactional
    public void deleteReservation(Transaction transaction){
        Objects.requireNonNull(transaction);
        transactionRepository.delete(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> filterTransactionsByAmountRange(float fromAmount, float toAmount) {
        return transactionRepository.findByAmountBetweenOrderByAmountAsc(fromAmount, toAmount);
    }
}
