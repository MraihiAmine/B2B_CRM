package B2B.CRM.dashboard.services.statistics;

import B2B.CRM.dashboard.entities.statistics.CustomerRetentionRateEntity;
import B2B.CRM.dashboard.entities.statistics.NpsEntity;
import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.entities.statistics.SalesGrowthRateEntity;
import B2B.CRM.dashboard.entities.statistics.SalesStatisticsEntity;
import B2B.CRM.dashboard.repositories.statisitics.CustomerRetentionRateRepository;
import B2B.CRM.dashboard.repositories.statisitics.NpsRepository;
import B2B.CRM.dashboard.repositories.statisitics.ProductRepository;
import B2B.CRM.dashboard.repositories.statisitics.SalesGrowthRateRepository;
import B2B.CRM.dashboard.repositories.statisitics.SalesStatisticsRepository;
import B2B.CRM.dashboard.services.acoounts.UserService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class SalesSatisticsService {
  @Autowired
  private UserService userService;
  
  @Autowired
  private SalesStatisticsRepository salesStatisticsRepository;

  @Autowired
  private CustomerRetentionRateRepository customerRetentionRateRepository;

  @Autowired
  private NpsRepository npsRepository;

  @Autowired
  private SalesGrowthRateRepository salesGrowthRateRepository;

  @Autowired
  private ProductRepository productRepository;

  public ModelAndView createSalesStatistics() {
    SalesStatisticsEntity salesStatistics = new SalesStatisticsEntity();

    List<Product> products = productRepository.findAll();
    salesStatisticsRepository.save(salesStatistics);
    ModelAndView modelAndView = new ModelAndView();
    String userRole = userService.getConnectedUserRole();
    modelAndView.addObject("userRole", userRole);
    modelAndView.addObject("products", products);
    modelAndView.setViewName("statistics/dashboard");
    return modelAndView;
  }
}
