package B2B.CRM.dashboard.controllers.statistics;

import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.entities.statistics.SalesGrowthRateEntity;
import B2B.CRM.dashboard.entities.statistics.YearStatistic;
import B2B.CRM.dashboard.repositories.statisitics.ProductRepository;
import B2B.CRM.dashboard.repositories.statisitics.SalesGrowthRateRepository;
import B2B.CRM.dashboard.repositories.statisitics.YearStatisticRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sgr")
public class SalesGrowthRateController {

  private final SalesGrowthRateRepository salesGrowthRateRepository;

  private final ProductRepository productRepository;

  private final YearStatisticRepository yearStatisticRepository;

  @Autowired
  SalesGrowthRateController(
    SalesGrowthRateRepository salesGrowthRateRepository,
    ProductRepository productRepository,
    YearStatisticRepository yearStatisticRepository
  ) {
    this.salesGrowthRateRepository = salesGrowthRateRepository;
    this.productRepository = productRepository;
    this.yearStatisticRepository = yearStatisticRepository;
  }

  @GetMapping("list")
  public String listProviders(Model model) {
    List<SalesGrowthRateEntity> la = salesGrowthRateRepository.findAll();
    if (la.size() == 0) la = null;
    model.addAttribute("sgrList", la);
    return "statistics/sgr/listSGR";
  }

  @GetMapping("add")
  public String showAddArticleForm(Model model) {
    List<Product> lp = productRepository.findAll();
    if (lp.isEmpty()) lp = null;
    List<YearStatistic> ly = yearStatisticRepository.findAll();
    if (ly.isEmpty()) ly = null;

    model.addAttribute("listProducts", lp);
    model.addAttribute("listYears", ly);
    model.addAttribute("sgr", new SalesGrowthRateEntity());
    return "statistics/sgr/addSGR";
  }

  @PostMapping("add")
  //@ResponseBody
  public String addArticle(
    Model model,
    @Valid SalesGrowthRateEntity salesGrowthRateEntity,
    @RequestParam(name = "yearId", required = true) Long y,
    @RequestParam(name = "productId", required = true) Long p,
    BindingResult result
  ) {
    if (result.hasErrors()) {
      model.addAttribute("sgr", salesGrowthRateEntity);
      return "statistics/sgr/addSGR";
    }

    Product product = productRepository
      .findById(p)
      .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + p)
      );

    salesGrowthRateEntity.setProduct(product);

    YearStatistic year = yearStatisticRepository
      .findById(y)
      .orElseThrow(() -> new IllegalArgumentException("Invalid year Id:" + y));

    System.out.println("year");
    System.out.println(year);

    salesGrowthRateEntity.setYearStatistic(year);

    salesGrowthRateEntity.calculateSalesGrowthRateQuarters();
    System.out.println(salesGrowthRateEntity.toString());
    salesGrowthRateRepository.save(salesGrowthRateEntity);
    return "redirect:list";
  }

  @GetMapping("delete/{id}")
  public String deleteProvider(@PathVariable("id") long id, Model model) {
    SalesGrowthRateEntity salesGrowthRateEntity = salesGrowthRateRepository
      .findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid provider Id:" + id)
      );
    salesGrowthRateRepository.delete(salesGrowthRateEntity);
    return "redirect:/sgr/list";
  }

  @GetMapping("edit/{id}")
  public String showArticleFormToUpdate(
    @PathVariable("id") long id,
    Model model
  ) {
    SalesGrowthRateEntity salesGrowthRateEntity = salesGrowthRateRepository
      .findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid provider Id:" + id)
      );

    List<Product> lp = productRepository.findAll();
    if (lp.isEmpty()) lp = null;
    List<YearStatistic> ly = yearStatisticRepository.findAll();
    if (ly.isEmpty()) ly = null;

    model.addAttribute("listProducts", lp);
    model.addAttribute("listYears", ly);

    model.addAttribute("sgr", salesGrowthRateEntity);
    return "statistics/sgr/updateSGR";
  }

  @PostMapping("edit")
  public String updateArticle(
    @Valid SalesGrowthRateEntity salesGrowthRateEntity,
    @RequestParam(name = "yearId", required = true) Long y,
    @RequestParam(name = "productId", required = true) Long p,
    BindingResult result,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("sgr", salesGrowthRateEntity);
      return "statistics/sgr/updateSGR";
    }

    Product product = productRepository
      .findById(p)
      .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + p)
      );

    salesGrowthRateEntity.setProduct(product);

    YearStatistic year = yearStatisticRepository
      .findById(y)
      .orElseThrow(() -> new IllegalArgumentException("Invalid year Id:" + y));

    System.out.println("year");
    System.out.println(year);

    salesGrowthRateEntity.setYearStatistic(year);
    salesGrowthRateRepository.save(salesGrowthRateEntity);
    return "redirect:list";
  }
}
