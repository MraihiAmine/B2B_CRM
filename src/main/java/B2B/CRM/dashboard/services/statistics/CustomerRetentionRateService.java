package B2B.CRM.dashboard.services.statistics;

import B2B.CRM.dashboard.entities.statistics.CustomerRetentionRateEntity;
import B2B.CRM.dashboard.entities.statistics.SalesStatisticsEntity;
import B2B.CRM.dashboard.repositories.statisitics.CustomerRetentionRateRepository;
import B2B.CRM.dashboard.repositories.statisitics.SalesStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerRetentionRateService {

    @Autowired
    private CustomerRetentionRateRepository customerRetentionRateRepository;

    @Autowired
    private SalesStatisticsRepository salesStatisticsRepository;



    public CustomerRetentionRateEntity create(CustomerRetentionRateEntity customerRetentionRateEntity) {
        customerRetentionRateEntity.setRetentionRate(customerRetentionRateEntity.calculateRetentionRate());

        return customerRetentionRateRepository.save(customerRetentionRateEntity);
    }

}
