package cz.cvut.fel.nss.transactions.transactionmodule.repository;

import cz.cvut.fel.nss.transactions.transactionmodule.entity.IncomeCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Integer> {
    // You can define additional methods specific to the IncomeCategoryRepository here if needed

    boolean existsByCategoryName(String categoryName);
}
