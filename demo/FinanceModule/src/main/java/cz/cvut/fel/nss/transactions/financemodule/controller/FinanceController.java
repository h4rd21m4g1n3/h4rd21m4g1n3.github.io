package cz.cvut.fel.nss.transactions.financemodule.controller;


import cz.cvut.fel.nss.transactions.financemodule.entity.Finance;
import cz.cvut.fel.nss.transactions.financemodule.repository.FinanceRepository;
import cz.cvut.fel.nss.transactions.financemodule.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finances")
public class FinanceController {

    private final FinanceService financeService;

    @Autowired
    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @Autowired
    private FinanceRepository financeRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getFinanceById(@PathVariable("id") int id) {
        try {
            Finance finance = financeService.getFinanceById(id);
            if (finance == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Finance with id " + id + " not found");
            }
            return ResponseEntity.ok().body(finance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
        }
    }
}
