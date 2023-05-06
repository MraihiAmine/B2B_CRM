package B2B.CRM.dashboard.entities.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sales_growth_rate")
public class SalesGrowthRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String product;
    private BigDecimal actualSales;
    private BigDecimal previousSales;
    private BigDecimal salesGrowthRate;

    // Method to calculate the sales growth rate
    public BigDecimal calculateSalesGrowthRate() {
        if (previousSales.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return (actualSales.subtract(previousSales)).divide(previousSales, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }

}
