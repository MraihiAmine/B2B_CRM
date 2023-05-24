package B2B.CRM.dashboard.repositories.prospections;

import org.springframework.data.jpa.repository.JpaRepository;

import B2B.CRM.dashboard.entities.prospections.AverageDealValue;

public interface AverageDealValueRepository extends JpaRepository<AverageDealValue, Long> {
  AverageDealValue findByProductIdAndYearStatisticId(Long productId, Long yearId);
}