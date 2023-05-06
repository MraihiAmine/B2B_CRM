package B2B.CRM.dashboard.repositories.statisitics;
import B2B.CRM.dashboard.entities.statistics.CustomerRetentionRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository("CustomerRetentionRateRepository")
public interface CustomerRetentionRateRepository extends JpaRepository<CustomerRetentionRateEntity, Long> {
    Optional<CustomerRetentionRateEntity> getCustomerRetentionRateByProductAndDate(String product, LocalDate date);
}
