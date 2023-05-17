package B2B.CRM.dashboard.controllers.statistics;

import B2B.CRM.dashboard.entities.statistics.CustomerRetentionRateEntity;
import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.entities.statistics.YearStatistic;
import B2B.CRM.dashboard.repositories.statisitics.CustomerRetentionRateRepository;
import B2B.CRM.dashboard.repositories.statisitics.ProductRepository;
import B2B.CRM.dashboard.repositories.statisitics.YearStatisticRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/crr/")
public class CustomerRetentionRateController {

  private final CustomerRetentionRateRepository customerRetentionRateRepository;

  private final ProductRepository productRepository;

  private final YearStatisticRepository yearStatisticRepository;

  @Autowired
  CustomerRetentionRateController(
    CustomerRetentionRateRepository customerRetentionRateRepository,
    ProductRepository productRepository,
    YearStatisticRepository yearStatisticRepository
  ) {
    this.customerRetentionRateRepository = customerRetentionRateRepository;
    this.productRepository = productRepository;
    this.yearStatisticRepository = yearStatisticRepository;
  }

  @GetMapping("list")
  public String listProviders(Model model) {
    List<CustomerRetentionRateEntity> la = customerRetentionRateRepository.findAll();
    if (la.size() == 0) la = null;
    model.addAttribute("crrList", la);
    return "statistics/crr/listCRR";
  }

  @GetMapping("add")
  public String showAddArticleForm(Model model) {
    List<Product> lp = productRepository.findAll();
    if (lp.isEmpty()) lp = null;
    List<YearStatistic> ly = yearStatisticRepository.findAll();
    if (ly.isEmpty()) ly = null;

    model.addAttribute("listProducts", lp);
    model.addAttribute("listYears", ly);
    model.addAttribute("crr", new CustomerRetentionRateEntity());
    return "statistics/crr/addCRR";
  }

  @PostMapping("add")
  //@ResponseBody
  public String addArticle(
    Model model,
    @Valid CustomerRetentionRateEntity customerRetentionRateEntity,
    @RequestParam(name = "yearId", required = true) Long y,
    @RequestParam(name = "productId", required = true) Long p,
    BindingResult result
  ) {
    if (result.hasErrors()) {
      model.addAttribute("crr", customerRetentionRateEntity);
      return "statistics/crr/addCRR";
    }

    Product product = productRepository
      .findById(p)
      .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + p)
      );
    

    customerRetentionRateEntity.setProduct(product);

    YearStatistic year = yearStatisticRepository
      .findById(y)
      .orElseThrow(() -> new IllegalArgumentException("Invalid year Id:" + y)
      );

      System.out.println("year");
      System.out.println(year);

    customerRetentionRateEntity.setYearStatistic(year);

    customerRetentionRateEntity.calculateRetentionRateQuarters();
    customerRetentionRateRepository.save(customerRetentionRateEntity);
    return "redirect:list";
  }

  @GetMapping("delete/{id}")
  public String deleteProvider(@PathVariable("id") long id, Model model) {
    CustomerRetentionRateEntity customerRetentionRateEntity = customerRetentionRateRepository
      .findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid provider Id:" + id)
      );
    customerRetentionRateRepository.delete(customerRetentionRateEntity);
    return "redirect:/crr/list";
  }

  @GetMapping("edit/{id}")
  public String showArticleFormToUpdate(
    @PathVariable("id") long id,
    Model model
  ) {
    CustomerRetentionRateEntity customerRetentionRateEntity = customerRetentionRateRepository
      .findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid provider Id:" + id)
      );

    model.addAttribute("crr", customerRetentionRateEntity);
    return "statistics/crr/updateCRR";
  }

  @PostMapping("edit")
  public String updateArticle(
    @Valid CustomerRetentionRateEntity customerRetentionRateEntity,
    BindingResult result,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("crr", customerRetentionRateEntity);
      return "statistics/crr/updateCRR";
    }
    customerRetentionRateRepository.save(customerRetentionRateEntity);
    return "redirect:listCRR";
  }
}
