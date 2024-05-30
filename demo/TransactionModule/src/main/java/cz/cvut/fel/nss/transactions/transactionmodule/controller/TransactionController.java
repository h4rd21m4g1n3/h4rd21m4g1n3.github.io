package cz.cvut.fel.nss.transactions.transactionmodule.controller;

import cz.cvut.fel.nss.transactions.transactionmodule.entity.Expense;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Income;
import cz.cvut.fel.nss.transactions.transactionmodule.entity.Transaction;

import cz.cvut.fel.nss.transactions.transactionmodule.entity.elasticentity.TransactionDocument;
import cz.cvut.fel.nss.transactions.transactionmodule.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    //tested
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") int id,  @RequestParam int userId) {
        Transaction transaction = transactionService.getTransactionById(id, userId);
        return ResponseEntity.ok().body(transaction);
    }

    //not working???
    @GetMapping("/all_transactions_desc")
    public ResponseEntity<List<Transaction>> getAllTransactionsDesc(@RequestParam int userId) {
        List<Transaction> transactions = transactionService.getAllExpensesDescendingOrder(userId);
        return ResponseEntity.ok().body(transactions);
    }

    //not working???
    @GetMapping("/all_transactions_asc")
    public ResponseEntity<List<Transaction>> getAllTransactionsAsc(@RequestParam int userId) {
        List<Transaction> transactions = transactionService.getAllExpensesAscendingOrder(userId);
        return ResponseEntity.ok().body(transactions);
    }



    //tested
    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions(@RequestParam int userId) {
        List<Transaction> transactions = transactionService.getAllTransactions(userId);
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

//    @GetMapping("/search")
//    public List<TransactionDocument> searchTransactions(@RequestParam String searchTerm) {
//        return transactionService.searchTransactions(searchTerm);
//    }
}
