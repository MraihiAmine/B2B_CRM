package B2B.CRM.dashboard.entities.statistics;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;


@AllArgsConstructor
@NoArgsConstructor
// @RequiredArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "customer_retention_rate")
public class CustomerRetentionRateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  // Total customers for Q1
  @NotNull(message = "totalCustomersQ1 is mandatory")
  @Column(name = "total_customers_q1")
  private BigDecimal totalCustomersQ1;

  // Returning customers for Q1
  @NotNull(message = "returningCustomersQ1 is mandatory")
  @Column(name = "returning_customers_q1")
  private BigDecimal returningCustomersQ1;

  // Total customers for Q2
  @NotNull(message = "totalCustomersQ2 is mandatory")
  @Column(name = "total_customers_q2")
  private BigDecimal totalCustomersQ2;

  // Returning customers for Q2
  @NotNull(message = "returningCustomersQ2 is mandatory")
  @Column(name = "returning_customers_q2")
  private BigDecimal returningCustomersQ2;

  // Total customers for Q3
  @NotNull(message = "totalCustomersQ3 is mandatory")
  @Column(name = "total_customers_q3")
  private BigDecimal totalCustomersQ3;

  // Returning customers for Q3
  @NotNull(message = "returningCustomersQ3 is mandatory")
  @Column(name = "returning_customers_q3")
  private BigDecimal returningCustomersQ3;

  // Total customers for Q4
  @NotNull(message = "totalCustomersQ4 is mandatory")
  @Column(name = "total_customers_q4")
  private BigDecimal totalCustomersQ4;

  // Returning customers for Q4
  @NotNull(message = "returningCustomersQ4 is mandatory")
  @Column(name = "returning_customers_q4")
  private BigDecimal returningCustomersQ4;

  private BigDecimal retentionRateQ1;
  private BigDecimal retentionRateQ2;
  private BigDecimal retentionRateQ3;
  private BigDecimal retentionRateQ4;

  // Calculate retention rate based on total customers and returning customers
  public BigDecimal calculateRetentionRate(
    BigDecimal totalCustomersParam,
    BigDecimal returningCustomersParam
  ) {
    if (
      totalCustomersParam == null ||
      returningCustomersParam == null ||
      totalCustomersParam.compareTo(BigDecimal.ZERO) == 0
    ) {
      return ((BigDecimal.ZERO));
    }
    BigDecimal retentionRate = returningCustomersParam
      .divide(totalCustomersParam, 4, RoundingMode.HALF_UP)
      .multiply(BigDecimal.valueOf(100));
    return ((retentionRate.setScale(2, RoundingMode.HALF_UP)));
  }

  public void calculateRetentionRateQuarters(){
    setRetentionRateQ1(calculateRetentionRate(
        totalCustomersQ1,
        returningCustomersQ1
      ));
    setRetentionRateQ2(calculateRetentionRate(
        totalCustomersQ2,
        returningCustomersQ2
      ));
    setRetentionRateQ3(calculateRetentionRate(
        totalCustomersQ3,
        returningCustomersQ3
      ));
    setRetentionRateQ4(calculateRetentionRate(
        totalCustomersQ4,
        returningCustomersQ4
      ));
  }

}


