package B2B.CRM.dashboard.entities.statistics;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import B2B.CRM.dashboard.entities.prospections.AverageDealValue;
import B2B.CRM.dashboard.entities.prospections.ConversionRate;
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
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Product name is mandatory")
  @Column(name = "name")
  private String name;

  @NotBlank(message = "Product description is mandatory")
  @Column(name = "description")
  private String description;

  @Column(name = "price")
  private float price;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
  private List<CustomerRetentionRateEntity> customerRetentionRateEntities;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
  private List<SalesGrowthRateEntity> salesGrowthRateEntities;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
  private List<ConversionRate> conversionRateEntities;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
  private List<AverageDealValue> averageDealValues;
}
