package cz.cvut.fel.nss.transactions.transactionmodule.repository;

import cz.cvut.fel.nss.transactions.transactionmodule.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Integer> {
    // You can define additional methods specific to the ExpenseCategoryRepository here if needed
}

