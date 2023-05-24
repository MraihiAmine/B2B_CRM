package B2B.CRM.dashboard.repositories.prospections;

import org.springframework.data.jpa.repository.JpaRepository;

import B2B.CRM.dashboard.entities.prospections.ConversionRate;

public interface ConversionRateRepository extends JpaRepository<ConversionRate, Long> {
  ConversionRate findByProductIdAndYearStatisticId(Long productId, Long yearId);
}
