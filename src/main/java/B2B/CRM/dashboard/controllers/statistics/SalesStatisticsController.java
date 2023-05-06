package B2B.CRM.dashboard.controllers.statistics;

import B2B.CRM.dashboard.DAO.statistics.SalesStatisticsRequest;
import B2B.CRM.dashboard.entities.accounts.User;
import B2B.CRM.dashboard.entities.statistics.SalesStatisticsEntity;
import B2B.CRM.dashboard.repositories.statisitics.CustomerRetentionRateRepository;
import B2B.CRM.dashboard.repositories.statisitics.NpsRepository;
import B2B.CRM.dashboard.repositories.statisitics.SalesGrowthRateRepository;
import B2B.CRM.dashboard.repositories.statisitics.SalesStatisticsRepository;
import B2B.CRM.dashboard.services.statistics.SalesSatisticsService;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/sales-statistics")
public class SalesStatisticsController {

  @Autowired
  private SalesStatisticsRepository salesStatisticsRepository;

  @Autowired
  private CustomerRetentionRateRepository customerRetentionRateRepository;

  @Autowired
  private NpsRepository npsRepository;

  @Autowired
  private SalesGrowthRateRepository salesGrowthRateRepository;

  @Autowired
  private SalesSatisticsService salesSatisticsService;

  @GetMapping("/{id}")
  public ResponseEntity<SalesStatisticsEntity> getSalesStatisticsById(
    @PathVariable Long id
  ) {
    Optional<SalesStatisticsEntity> salesStatistics = salesStatisticsRepository.findById(
      id
    );
    return salesStatistics
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @RequestMapping(value = "/salesDashboard/{id}", method = RequestMethod.GET)
  public ModelAndView salesDashboard(@PathVariable Long id) {
    Optional<SalesStatisticsEntity> salesStatistics = salesStatisticsRepository.findById(
      id
    );
    SalesStatisticsEntity salesStatisticsEntity = salesStatistics.orElse(
      new SalesStatisticsEntity()
    );
    System.out.println("salesStatistics" + salesStatisticsEntity.toString());
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("salesStatistics", salesStatisticsEntity);
    modelAndView.setViewName("statistics/dashboard");
    return modelAndView;
  }

  @RequestMapping(
    value = "/dashboard",
    method = RequestMethod.GET
  )
  public ModelAndView dashboard(
  ) {
    return salesSatisticsService.createSalesStatistics();
  }
  //   @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  //   public ModelAndView dashboard() {
  //     ModelAndView modelAndView = new ModelAndView();
  //     modelAndView.setViewName("statistics/dashboard");
  //     return modelAndView;
  //   }

  //   @PostMapping("/")
  //   public ResponseEntity<SalesStatisticsEntity> createSalesStatistics(
  //     @RequestBody SalesStatisticsRequest request
  //   ) {
  //     return ResponseEntity.ok(
  //       salesSatisticsService.createSalesStatistics(request)
  //     );
  //   }
}
