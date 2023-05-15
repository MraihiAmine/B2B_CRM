package B2B.CRM.dashboard.entities.statistics;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "year")
public class YearStatistic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Year cannot be null")
  @Min(value = 2010, message = "Year must be at least 2010")
  @Max(value = 2050, message = "Year cannot be after 2050")
  private int year;

  // @OneToMany(cascade=CascadeType.ALL, mappedBy = "yearStatistic")
  // private List<CustomerRetentionRateEntity> customerRetentionRateEntities;
}
