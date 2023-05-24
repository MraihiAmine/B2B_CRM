package B2B.CRM.dashboard.services.prospections;

import B2B.CRM.dashboard.entities.prospections.ConversionRate;
import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.entities.statistics.YearStatistic;
import B2B.CRM.dashboard.repositories.prospections.ConversionRateRepository;
import B2B.CRM.dashboard.repositories.statisitics.ProductRepository;
import B2B.CRM.dashboard.repositories.statisitics.YearStatisticRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConverionRateService {

  private final ConversionRateRepository conversionRateRepository;
  private final EntityManager entityManager;
  private final ProductRepository productRepository;
  private final YearStatisticRepository yearStatisticRepository;

  @Autowired
  public ConverionRateService(
    ConversionRateRepository conversionRateRepository,
    EntityManager entityManager,
    ProductRepository productRepository,
    YearStatisticRepository yearStatisticRepository
  ) {
    this.conversionRateRepository = conversionRateRepository;
    this.entityManager = entityManager;
    this.productRepository = productRepository;
    this.yearStatisticRepository = yearStatisticRepository;
  }

  public void calculateAndSaveConversionRates(List<String> statusIds) {
    for (String statusId : statusIds) {
      String jpaQuery =
        "SELECT p.product.id, p.yearStatistic.id, " +
        "       COUNT(CASE WHEN " +
        statusId +
        " = 1 THEN 1 END) * 100 / COUNT(*) AS percentage " +
        "FROM Prospection p " +
        "GROUP BY p.product.id, p.yearStatistic.id";

      Query query = entityManager.createQuery(jpaQuery);
      List<Object[]> results = query.getResultList();

      for (Object[] result : results) {
        Long productId = (Long) result[0];
        Long yearId = (Long) result[1];
        double percentage = Double.parseDouble(result[2].toString());

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

        ConversionRate conversionRate = conversionRateRepository.findByProductIdAndYearStatisticId(
          productId,
          yearId
        );

        if (conversionRate != null) {
          setConversionRateByStatusId(conversionRate, statusId, percentage);
        } else {
          conversionRate = new ConversionRate();
          conversionRate.setProduct(product);
          conversionRate.setYearStatistic(yearStatistic);
          setConversionRateByStatusId(conversionRate, statusId, percentage);
        }

        conversionRateRepository.save(conversionRate);
      }
    }
  }

  private void setConversionRateByStatusId(
    ConversionRate conversionRate,
    String statusId,
    double percentage
  ) {
    switch (statusId) {
      case "prospection_status_id1":
        conversionRate.setConversionRateQ1(percentage);
        break;
      case "prospection_status_id2":
        conversionRate.setConversionRateQ2(percentage);
        break;
      case "prospection_status_id3":
        conversionRate.setConversionRateQ3(percentage);
        break;
      case "prospection_status_id4":
        conversionRate.setConversionRateQ4(percentage);
        break;
      default:
        throw new IllegalArgumentException("Invalid status Id:" + statusId);
    }
  }
}
