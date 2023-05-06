package B2B.CRM.dashboard.DAO.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesStatisticsRequest {
    private LocalDate date;

    private String product;

}
