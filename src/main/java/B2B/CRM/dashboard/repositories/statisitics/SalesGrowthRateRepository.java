package B2B.CRM.dashboard.repositories.statisitics;

import B2B.CRM.dashboard.entities.statistics.SalesGrowthRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesGrowthRateRepository extends JpaRepository<SalesGrowthRateEntity, Long> {
    // add custom query methods here if needed
}

