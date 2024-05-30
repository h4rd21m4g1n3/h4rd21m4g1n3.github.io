package cz.cvut.fel.nss.transactions.financemodule.service;


import cz.cvut.fel.nss.transactions.financemodule.entity.Debt;
import cz.cvut.fel.nss.transactions.financemodule.repository.DebtRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class DebtService {
    private final DebtRepository debtRepository;

    public DebtService(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    public Debt getDebtById(int debtId, int userId){
        Optional<Debt> optionalIncome = debtRepository.findByIdAndUserId(debtId, userId);
        return optionalIncome.orElseThrow(() -> new RuntimeException("Debt not found with id: " + debtId + " for user: " + userId));
    }

    @Transactional
    public void createDebt(Debt debt) {
        Objects.requireNonNull(debt);
        debtRepository.save(debt);
    }

    @Transactional
    public void updateDebt(Debt updatedDebt,  int userId) {
        Objects.requireNonNull(updatedDebt);
        Debt existingDebt = getDebtById(updatedDebt.getId(), userId);
        existingDebt.setAmount(updatedDebt.getAmount());
        existingDebt.setName(updatedDebt.getName());
        existingDebt.setNameOfPersonToGiveBack(updatedDebt.getNameOfPersonToGiveBack());
        existingDebt.setDueDate(updatedDebt.getDueDate());
        debtRepository.save(existingDebt);
    }

    @Transactional
    public void deleteDebt(int debtId, int userId) {
        getDebtById(debtId, userId);
        debtRepository.deleteById(debtId);
    }

//    public void addDebtMemento(DebtMementoDTO debtMementoDTO) {
//        DebtMemento debtMemento = new DebtMemento(debtMementoDTO.getName(), debtMementoDTO.getAmount(), debtMementoDTO.getNameOfPersonToGiveBack(), debtMementoDTO.getDueDate());
//        debtRepository.addMemento(debtMemento);
//    }
}
