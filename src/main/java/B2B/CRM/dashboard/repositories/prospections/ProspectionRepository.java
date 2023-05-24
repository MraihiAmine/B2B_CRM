package B2B.CRM.dashboard.repositories.prospections;

import B2B.CRM.dashboard.entities.prospections.ConversionRate;
import B2B.CRM.dashboard.entities.prospections.Prospection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProspectionRepository
  extends JpaRepository<Prospection, Long> {
}
