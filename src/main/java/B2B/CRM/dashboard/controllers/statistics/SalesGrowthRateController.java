package B2B.CRM.dashboard.controllers.statistics;


import B2B.CRM.dashboard.entities.statistics.SalesGrowthRateEntity;
import B2B.CRM.dashboard.repositories.statisitics.SalesGrowthRateRepository;
import B2B.CRM.dashboard.services.statistics.SalesGrowthRateRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SalesGrowthRateController {

    @Autowired
    private SalesGrowthRateRepository salesRepository;

    @Autowired
    private SalesGrowthRateRateService salesGrowthRateRateService;

    @GetMapping("/{id}")
    public ResponseEntity<SalesGrowthRateEntity> getSalesGrowthRateById(@PathVariable(value = "id") Long id) {
        Optional<SalesGrowthRateEntity> salesStatistics = salesRepository.findById(id);
        return salesStatistics.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SalesGrowthRateEntity> createSalesGrowthRate(@RequestBody SalesGrowthRateEntity salesGrowthRateEntity) {
        SalesGrowthRateEntity savedEntity = salesGrowthRateRateService.create(salesGrowthRateEntity);
        return ResponseEntity.ok(savedEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesGrowthRateEntity> updateSalesGrowthRate(@PathVariable(value = "id") Long id,
                                                                       @RequestBody SalesGrowthRateEntity salesDetails) {
        if (!salesRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        salesDetails.setId(id);
        salesDetails.setActualSales(salesDetails.getActualSales());
        salesDetails.setPreviousSales(salesDetails.getPreviousSales());
        salesDetails.setSalesGrowthRate(salesDetails.calculateSalesGrowthRate());

        SalesGrowthRateEntity updatedSales = salesRepository.save(salesDetails);
        return ResponseEntity.ok(updatedSales);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSalesGrowthRate(@PathVariable(value = "id") Long id) {
        if (!salesRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        salesRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
