package B2B.CRM.dashboard.entities.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "sales_statistics")
public class SalesStatisticsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String product;
    private BigDecimal salesGrowthRate;
    private BigDecimal customerRetentionRate;
    private Integer nps;

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "customer_retention_rate_id", referencedColumnName = "id")
    // private CustomerRetentionRateEntity customerRetentionRateEntity;

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "nps_id", referencedColumnName = "id")
    // private NpsEntity npsEntity;

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "sales_growthRate_id", referencedColumnName = "id")
    // private SalesGrowthRateEntity salesGrowthRateEntity;
}

