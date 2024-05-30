package cz.cvut.fel.nss.transactions.financemodule.service;


import cz.cvut.fel.nss.transactions.financemodule.entity.Debt;
import cz.cvut.fel.nss.transactions.financemodule.entity.Finance;
import cz.cvut.fel.nss.transactions.financemodule.repository.FinanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinanceService {

    private final FinanceRepository financeRepository;

    public FinanceService(FinanceRepository financeRepository) {
        this.financeRepository = financeRepository;
    }

    public Finance getFinanceById(int financeId){
        return financeRepository.findById(financeId)
                .orElseThrow(() -> new RuntimeException("Finance not found with id: " + financeId));
    }
}
