package B2B.CRM.dashboard.repositories.statisitics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import B2B.CRM.dashboard.entities.statistics.YearStatistic;

@Repository
public interface YearStatisticRepository extends JpaRepository<YearStatistic, Long> {
}

