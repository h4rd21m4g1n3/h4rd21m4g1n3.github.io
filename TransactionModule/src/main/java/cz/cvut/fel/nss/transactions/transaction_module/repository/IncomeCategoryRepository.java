package cz.cvut.fel.nss.transactions.transaction_module.repository;

import cz.cvut.fel.nss.transactions.transaction_module.entity.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Integer> {
    boolean existsByCategoryName(String categoryName);
}
