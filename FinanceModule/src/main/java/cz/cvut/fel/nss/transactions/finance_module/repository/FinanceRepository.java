package cz.cvut.fel.nss.transactions.finance_module.repository;


import cz.cvut.fel.nss.transactions.finance_module.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Integer> {


}
