package B2B.CRM.dashboard.entities.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
// @RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer_retention_rate")
public class CustomerRetentionRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;

    @NotBlank(message = "product is mandatory")
    @Column(name = "product")
    private String product;

    @NotNull(message = "total Customers is mandatory")
    @Column(name = "totalCustomers")
    private BigDecimal totalCustomers;

    @NotNull(message = "returning Customers is mandatory")
    @Column(name = "returningCustomers")
    private BigDecimal returningCustomers;
    private BigDecimal retentionRate;

    // Calculate retention rate based on total customers and returning customers
    public BigDecimal calculateRetentionRate() {
        if (totalCustomers == null || returningCustomers == null || totalCustomers.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal retentionRate = returningCustomers.divide(totalCustomers, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        return retentionRate.setScale(2, RoundingMode.HALF_UP);
    }
}

