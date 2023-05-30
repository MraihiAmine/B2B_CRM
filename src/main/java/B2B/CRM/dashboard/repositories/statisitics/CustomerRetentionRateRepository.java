package B2B.CRM.dashboard.repositories.statisitics;

import B2B.CRM.dashboard.entities.statistics.CustomerRetentionRateEntity;
import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.entities.statistics.YearStatistic;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("CustomerRetentionRateRepository")
public interface CustomerRetentionRateRepository
  extends JpaRepository<CustomerRetentionRateEntity, Long> {
  Optional<CustomerRetentionRateEntity> findByYearStatisticAndProduct(
    YearStatistic yearStatistic,
    Product product
  );
}
