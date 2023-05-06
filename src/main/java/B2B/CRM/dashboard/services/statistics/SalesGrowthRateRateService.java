package B2B.CRM.dashboard.services.statistics;

import B2B.CRM.dashboard.entities.statistics.SalesGrowthRateEntity;
import B2B.CRM.dashboard.entities.statistics.SalesStatisticsEntity;
import B2B.CRM.dashboard.repositories.statisitics.SalesGrowthRateRepository;
import B2B.CRM.dashboard.repositories.statisitics.SalesStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalesGrowthRateRateService {
    @Autowired
    private SalesGrowthRateRepository salesGrowthRateRepository;

    public SalesGrowthRateEntity create(SalesGrowthRateEntity salesGrowthRateEntity) {
        salesGrowthRateEntity.setSalesGrowthRate(salesGrowthRateEntity.calculateSalesGrowthRate());
        return salesGrowthRateRepository.save(salesGrowthRateEntity);
    }
}
