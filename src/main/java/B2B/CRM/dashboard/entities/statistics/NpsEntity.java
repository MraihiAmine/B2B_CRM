package B2B.CRM.dashboard.entities.statistics;

import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "nps")
public class NpsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "product is mandatory")
  @Column(name = "product")
  private String product;

//   @NotBlank(message = "date is mandatory")
//   @Column(name = "date")
  private LocalDate date;

  @Min(value = 0, message = "nps value must be at least 0")
  @Max(value = 10, message = "nps value must be at most 10")
  private Integer nps;

  @NotBlank(message = "Comment is mandatory")
  @Column(name = "comment")
  private String comment;
}
