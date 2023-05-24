package B2B.CRM.dashboard.entities.prospections;
import com.fasterxml.jackson.annotation.JsonIgnore;

import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.entities.statistics.YearStatistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.Min;
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
@Table(name = "conversion_rate")
public class ConversionRate {
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

  @NotNull(message = "conversionRate Q1 cannot be null")
  @Min(value = 0, message = "conversionRate Q1 must be at least 0")
  private double conversionRateQ1;
  
  @NotNull(message = "conversionRate Q2 cannot be null")
  @Min(value = 0, message = "conversionRate Q2 must be at least 0")
  private double conversionRateQ2;
  
  @NotNull(message = "conversionRate Q3 cannot be null")
  @Min(value = 0, message = "conversionRate Q3 must be at least 0")
  private double conversionRateQ3;
  
  @NotNull(message = "conversionRate Q4 cannot be null")
  @Min(value = 0, message = "conversionRate Q4 must be at least 0")
  private double conversionRateQ4;
}
