package B2B.CRM.dashboard.services.prospections;

import B2B.CRM.dashboard.entities.prospections.AverageDealValue;
import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.entities.statistics.YearStatistic;
import B2B.CRM.dashboard.repositories.prospections.AverageDealValueRepository;
import B2B.CRM.dashboard.repositories.statisitics.ProductRepository;
import B2B.CRM.dashboard.repositories.statisitics.YearStatisticRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AverageDealValueService {

  private final AverageDealValueRepository averageDealRepository;
  private final EntityManager entityManager;
  private final ProductRepository productRepository;
  private final YearStatisticRepository yearStatisticRepository;

  @Autowired
  public AverageDealValueService(
    AverageDealValueRepository averageDealRepository,
    EntityManager entityManager,
    ProductRepository productRepository,
    YearStatisticRepository yearStatisticRepository
  ) {
    this.averageDealRepository = averageDealRepository;
    this.entityManager = entityManager;
    this.productRepository = productRepository;
    this.yearStatisticRepository = yearStatisticRepository;
  }

  public void calculateAndSaveAverageDeals() {
    String jpaQuery =
      "SELECT p.yearStatistic.id, p.product.id, " +
      "       SUM(CASE WHEN p.prospectionStatusQ1.id = 1 THEN p.valueQ1 ELSE 0 END) / " +
      "       COUNT(CASE WHEN p.prospectionStatusQ1.id = 1 THEN 1 ELSE NULL END) AS averageDealValueQ1, " +
      "       SUM(CASE WHEN p.prospectionStatusQ2.id = 1 THEN p.valueQ2 ELSE 0 END) / " +
      "       COUNT(CASE WHEN p.prospectionStatusQ2.id = 1 THEN 1 ELSE NULL END) AS averageDealValueQ2, " +
      "       SUM(CASE WHEN p.prospectionStatusQ3.id = 1 THEN p.valueQ3 ELSE 0 END) / " +
      "       COUNT(CASE WHEN p.prospectionStatusQ3.id = 1 THEN 1 ELSE NULL END) AS averageDealValueQ3, " +
      "       SUM(CASE WHEN p.prospectionStatusQ4.id = 1 THEN p.valueQ4 ELSE 0 END) / " +
      "       COUNT(CASE WHEN p.prospectionStatusQ4.id = 1 THEN 1 ELSE NULL END) AS averageDealValueQ4 " +
      "FROM Prospection p " +
      "GROUP BY p.yearStatistic.id, p.product.id";

    Query query = entityManager.createQuery(jpaQuery);
    List<Object[]> results = query.getResultList();

    for (Object[] result : results) {
      Long yearId = (Long) result[0];
      Long productId = (Long) result[1];
      double averageDealValueQ1 = (Double) result[2];
      double averageDealValueQ2 = (Double) result[3];
      double averageDealValueQ3 = (Double) result[4];
      double averageDealValueQ4 = (Double) result[5];

      Product product = productRepository
        .findById(productId)
        .orElseThrow(() ->
          new IllegalArgumentException("Invalid product Id:" + productId)
        );

      YearStatistic yearStatistic = yearStatisticRepository
        .findById(yearId)
        .orElseThrow(() ->
          new IllegalArgumentException("Invalid year Id:" + yearId)
        );

      AverageDealValue averageDeal = averageDealRepository.findByProductIdAndYearStatisticId(
        productId,
        yearId
      );

      if (averageDeal == null) {
        averageDeal = new AverageDealValue();
        averageDeal.setProduct(product);
        averageDeal.setYearStatistic(yearStatistic);
      }
      averageDeal.setAverageDealQ1(averageDealValueQ1);
      averageDeal.setAverageDealQ2(averageDealValueQ2);
      averageDeal.setAverageDealQ3(averageDealValueQ3);
      averageDeal.setAverageDealQ4(averageDealValueQ4);
      averageDealRepository.save(averageDeal);
    }
  }
}
