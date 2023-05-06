
package B2B.CRM.dashboard.repositories.statisitics;

import B2B.CRM.dashboard.entities.statistics.NpsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface NpsRepository extends JpaRepository<NpsEntity, Long> {
    Optional<NpsEntity> getNpsByProductAndDate(String product, LocalDate date);
}
