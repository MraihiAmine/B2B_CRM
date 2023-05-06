package B2B.CRM.dashboard.controllers.statistics;

import B2B.CRM.dashboard.entities.statistics.CustomerRetentionRateEntity;
import B2B.CRM.dashboard.repositories.statisitics.CustomerRetentionRateRepository;
import B2B.CRM.dashboard.repositories.statisitics.SalesStatisticsRepository;
import B2B.CRM.dashboard.services.statistics.CustomerRetentionRateService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/crr")
public class CustomerRetentionRateController {

  @Autowired
  private CustomerRetentionRateRepository customerRetentionRateRepository;

  @Autowired
  private SalesStatisticsRepository salesStatisticsRepository;

  @Autowired
  private CustomerRetentionRateService customerRetentionRateService;

  @GetMapping("add")
  public ModelAndView showAddArticleForm() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("crr", new CustomerRetentionRateEntity());
    modelAndView.setViewName("statistics/crr/addCRR");
    return modelAndView;
  }

  @PostMapping("add")
  public ModelAndView addArticle(
    @Valid CustomerRetentionRateEntity customerRetentionRateEntity,
    BindingResult result
  ) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("crr", customerRetentionRateEntity);
    if (result.hasErrors()) {
      modelAndView.setViewName("statistics/crr/addCRR");
      System.out.println("result: " + result);
      return modelAndView;
    }
    customerRetentionRateEntity.setRetentionRate(
      customerRetentionRateEntity.calculateRetentionRate()
    );
    customerRetentionRateRepository.save(customerRetentionRateEntity);
    modelAndView.setViewName("statistics/dashboard");
    return modelAndView;
  }

  @GetMapping("list")
  public ModelAndView listProviders() {
    List<CustomerRetentionRateEntity> la = customerRetentionRateRepository.findAll();
    ModelAndView modelAndView = new ModelAndView();
    if (la.isEmpty()) la = null;
    modelAndView.addObject("crrList", la);
    modelAndView.setViewName("statistics/crr/listCRR");
    return modelAndView;
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerRetentionRateEntity> update(
    @PathVariable Long id,
    @RequestBody CustomerRetentionRateEntity entity
  ) {
    if (!customerRetentionRateRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }
    entity.setId(id);
    CustomerRetentionRateEntity savedEntity = customerRetentionRateRepository.save(
      entity
    );
    return ResponseEntity.ok(savedEntity);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!customerRetentionRateRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }
    customerRetentionRateRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
