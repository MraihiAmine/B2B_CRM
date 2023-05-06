package B2B.CRM.dashboard.services.statistics;
import B2B.CRM.dashboard.entities.statistics.NpsEntity;
import B2B.CRM.dashboard.entities.statistics.SalesGrowthRateEntity;
import B2B.CRM.dashboard.entities.statistics.SalesStatisticsEntity;
import B2B.CRM.dashboard.repositories.statisitics.NpsRepository;
import B2B.CRM.dashboard.repositories.statisitics.SalesGrowthRateRepository;
import B2B.CRM.dashboard.repositories.statisitics.SalesStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NpsEntityService {
    @Autowired
    private SalesStatisticsRepository salesStatisticsRepository;

    @Autowired
    private NpsRepository npsRepository;

    public NpsEntity create(NpsEntity npsEntity) {

        return npsRepository.save(npsEntity);
    }
}
