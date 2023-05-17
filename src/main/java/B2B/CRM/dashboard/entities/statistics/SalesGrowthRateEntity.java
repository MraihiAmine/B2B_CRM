package B2B.CRM.dashboard.entities.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "sales_growth_rate")
public class SalesGrowthRateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Product product;

  // @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "year_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private YearStatistic yearStatistic;

  @NotNull(message = "actualSalesQ1 is mandatory")
  @Column(name = "actual_sales_q1")
  private BigDecimal actualSalesQ1;

  @NotNull(message = "actualSalesQ2 is mandatory")
  @Column(name = "actual_sales_q2")
  private BigDecimal actualSalesQ2;

  @NotNull(message = "actualSalesQ3 is mandatory")
  @Column(name = "actual_sales_q3")
  private BigDecimal actualSalesQ3;

  @NotNull(message = "actualSalesQ4 is mandatory")
  @Column(name = "actual_sales_q4")
  private BigDecimal actualSalesQ4;

  private BigDecimal salesGrowthRateQ1;
  private BigDecimal salesGrowthRateQ2;
  private BigDecimal salesGrowthRateQ3;
  private BigDecimal salesGrowthRateQ4;

  public static BigDecimal calculateSalesGrowthRate(
    BigDecimal currentSales,
    BigDecimal previousSales
  ) {
    BigDecimal growthRate = BigDecimal.ZERO;

    if (previousSales.compareTo(BigDecimal.ZERO) != 0) {
      BigDecimal salesDifference = currentSales.subtract(previousSales);
      growthRate =
        salesDifference
          .divide(previousSales, 4, RoundingMode.HALF_UP)
          .multiply(new BigDecimal(100));
    }

    return growthRate;
  }

  // Function to calculate sales growth rates for all quarters
  public void calculateSalesGrowthRateQuarters() {
    // Calculate sales growth rates for each quarter
    setSalesGrowthRateQ1(
      calculateSalesGrowthRate(actualSalesQ1, BigDecimal.ZERO)
    );
    setSalesGrowthRateQ2(
      calculateSalesGrowthRate(actualSalesQ2, actualSalesQ1)
    );
    setSalesGrowthRateQ3(
      calculateSalesGrowthRate(actualSalesQ3, actualSalesQ2)
    );
    setSalesGrowthRateQ4(
      calculateSalesGrowthRate(actualSalesQ4, actualSalesQ3)
    );
  }
}
