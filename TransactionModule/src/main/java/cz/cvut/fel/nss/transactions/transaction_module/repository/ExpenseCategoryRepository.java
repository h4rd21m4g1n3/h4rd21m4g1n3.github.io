package cz.cvut.fel.nss.transactions.transaction_module.repository;

import cz.cvut.fel.nss.transactions.transaction_module.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Integer> {
}

