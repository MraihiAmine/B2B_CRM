package B2B.CRM.dashboard.entities.prospections;

import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.entities.statistics.YearStatistic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "prospection")
public class Prospection {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "companyName name is mandatory")
  @Column(name = "companyName")
  private String companyName;

  @NotBlank(message = "email name is mandatory")
  @Email
  @Column(name = "email")
  private String email;

  @NotBlank(message = "phone name is mandatory")
  @Column(name = "phone")
  private String phone;

  // @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Product product;

  // @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "year_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private YearStatistic yearStatistic;

  // @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "prospection_status_id1", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ProspectionStatus prospectionStatusQ1;

  // @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "prospection_status_id2", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ProspectionStatus prospectionStatusQ2;

  // @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "prospection_status_id3", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ProspectionStatus prospectionStatusQ3;

  // @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "prospection_status_id4", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ProspectionStatus prospectionStatusQ4;

  @NotNull(message = "value Q1 cannot be null")
  @Min(value = 0, message = "value Q1 must be at least 0")
  private double valueQ1;
  
  @NotNull(message = "value Q2 cannot be null")
  @Min(value = 0, message = "value Q2 must be at least 0")
  private double valueQ2;
  
  @NotNull(message = "value Q3 cannot be null")
  @Min(value = 0, message = "value Q3 must be at least 0")
  private double valueQ3;
  
  @NotNull(message = "value Q4 cannot be null")
  @Min(value = 0, message = "value Q4 must be at least 0")
  private double valueQ4;
}
