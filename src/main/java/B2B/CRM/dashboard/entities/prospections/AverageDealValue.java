package B2B.CRM.dashboard.entities.prospections;

import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.entities.statistics.YearStatistic;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "average_deal")
public class AverageDealValue {
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
  
  @NotNull(message = "averageDeal Q1 cannot be null")
  @Min(value = 0, message = "averageDeal Q1 must be at least 0")
  private double averageDealQ1;
  
  @NotNull(message = "averageDeal Q2 cannot be null")
  @Min(value = 0, message = "averageDeal Q2 must be at least 0")
  private double averageDealQ2;
  
  @NotNull(message = "averageDeal Q3 cannot be null")
  @Min(value = 0, message = "averageDeal Q3 must be at least 0")
  private double averageDealQ3;
  
  @NotNull(message = "averageDeal Q4 cannot be null")
  @Min(value = 0, message = "averageDeal Q4 must be at least 0")
  private double averageDealQ4;
}
