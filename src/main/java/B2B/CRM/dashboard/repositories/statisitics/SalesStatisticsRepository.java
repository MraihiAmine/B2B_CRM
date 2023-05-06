package B2B.CRM.dashboard.repositories.statisitics;

import B2B.CRM.dashboard.entities.statistics.SalesStatisticsEntity;
import org.hibernate.mapping.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SalesStatisticsRepository extends JpaRepository<SalesStatisticsEntity, Long> {
    @Query(value = "select * FROM sales_statistics ss where ss.product = :product and ss.date = :date", nativeQuery = true)
    Optional<SalesStatisticsEntity> findBySalesStatisticsEntityByProductAndDate(
            @Param("product") String product,@Param("date") LocalDate date);
}

