package B2B.CRM.dashboard.repositories.prospections;

import org.springframework.data.jpa.repository.JpaRepository;

import B2B.CRM.dashboard.entities.prospections.ProspectionStatus;


public interface ProspectionStatusRepository extends JpaRepository<ProspectionStatus, Long>  {
  
}
