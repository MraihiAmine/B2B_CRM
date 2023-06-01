package B2B.CRM.dashboard.repositories.statisitics;

import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.entities.statistics.SalesGrowthRateEntity;
import B2B.CRM.dashboard.entities.statistics.YearStatistic;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesGrowthRateRepository
  extends JpaRepository<SalesGrowthRateEntity, Long> {
  Optional<SalesGrowthRateEntity> findByYearStatisticAndProduct(
    YearStatistic yearStatistic,
    Product product
  );
}
