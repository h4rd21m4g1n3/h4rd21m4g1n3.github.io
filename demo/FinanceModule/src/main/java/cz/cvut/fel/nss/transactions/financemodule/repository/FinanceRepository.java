package cz.cvut.fel.nss.transactions.financemodule.repository;


import cz.cvut.fel.nss.transactions.financemodule.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Integer> {


}
