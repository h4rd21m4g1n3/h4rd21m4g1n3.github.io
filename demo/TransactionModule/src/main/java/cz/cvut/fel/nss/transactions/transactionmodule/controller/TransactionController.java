package cz.cvut.fel.nss.transactions.transactionmodule.controller;

import cz.cvut.fel.nss.transactions.transactionmodule.entity.Expense;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Income;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Transaction;

import cz.cvut.fel.nss.transactions.transactionmodule.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/transactions")
//@Tag(name = "Transactions", description = "")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") int id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok().body(transaction);
    }

    @GetMapping("/all_transactions_desc")
    public ResponseEntity<List<Transaction>> getAllTransactionsDesc() {
        List<Transaction> transactions = transactionService.getAllExpensesDescendingOrder();
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/all_transactions_asc")
    public ResponseEntity<List<Transaction>> getAllTransactionsAsc() {
        List<Transaction> transactions = transactionService.getAllExpensesAscendingOrder();
        return ResponseEntity.ok().body(transactions);
    }
//    @PostMapping
//    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
//        transactionService.createTransaction(transaction);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable("id") int id, @RequestBody Transaction updatedTransaction) {
        updatedTransaction.setId(id);
        transactionService.updateTransaction(updatedTransaction);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("id") int id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok().body(transactions);
    }


    @GetMapping("/filter-by-amount")
    public ResponseEntity<List<Transaction>> filterTransactionsByAmountRange(@RequestParam("from") float fromAmount,
                                                                     @RequestParam("to") float toAmount) {
        if (fromAmount > toAmount) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        List<Transaction> filteredTransactions = transactionService.filterTransactionsByAmountRange(fromAmount, toAmount);

        return ResponseEntity.ok().body(filteredTransactions);
    }
}
