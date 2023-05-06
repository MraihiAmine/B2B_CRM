package B2B.CRM.dashboard.repositories.statisitics;

import B2B.CRM.dashboard.entities.statistics.SalesGrowthRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SalesGrowthRateRepository extends JpaRepository<SalesGrowthRateEntity, Long> {
    Optional<SalesGrowthRateEntity> getSalesGrowthRateByProductAndDate(String product, LocalDate date);
    // add custom query methods here if needed
}

