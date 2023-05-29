package B2B.CRM.dashboard.controllers.prospections;

import B2B.CRM.dashboard.entities.accounts.User;
import B2B.CRM.dashboard.entities.prospections.Prospection;
import B2B.CRM.dashboard.entities.prospections.ProspectionStatus;
import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.entities.statistics.YearStatistic;
import B2B.CRM.dashboard.repositories.prospections.ProspectionRepository;
import B2B.CRM.dashboard.repositories.prospections.ProspectionStatusRepository;
import B2B.CRM.dashboard.repositories.statisitics.ProductRepository;
import B2B.CRM.dashboard.repositories.statisitics.YearStatisticRepository;
import B2B.CRM.dashboard.services.acoounts.UserService;
import B2B.CRM.dashboard.services.prospections.AverageDealValueService;
import B2B.CRM.dashboard.services.prospections.ConverionRateService;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prospection/")
public class prospectionController {

  private final ProspectionRepository prospectionRepository;
  private final ProductRepository productRepository;
  private final YearStatisticRepository yearStatisticRepository;
  private final ProspectionStatusRepository prospectionStatusRepository;
  private final ConverionRateService conversionRateService;
  private final AverageDealValueService averageDealValueService;

  @Autowired
  private UserService userService;

  @Autowired
  prospectionController(
    ProspectionRepository prospectionRepository,
    ProductRepository productRepository,
    YearStatisticRepository yearStatisticRepository,
    ProspectionStatusRepository prospectionStatusRepository,
    ConverionRateService conversionRateService,
    AverageDealValueService averageDealValueService
  ) {
    this.prospectionRepository = prospectionRepository;
    this.productRepository = productRepository;
    this.yearStatisticRepository = yearStatisticRepository;
    this.prospectionStatusRepository = prospectionStatusRepository;
    this.conversionRateService = conversionRateService;
    this.averageDealValueService = averageDealValueService;
  }

  @GetMapping("list")
  public String listProviders(Model model) {
    List<Prospection> la = prospectionRepository.findAll();
    if (la.size() == 0) la = null;
    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    User user = userService.findUserByEmail(auth.getName());
    System.out.println(user.getRoles());
    String userRole = user.getRoles().iterator().next().getRole();
    model.addAttribute("userRole", userRole);
    model.addAttribute("prospectionList", la);
    return "prospections/prospection/list";
  }

  @GetMapping("add")
  public String showAddArticleForm(Model model) {
    List<Product> lp = productRepository.findAll();
    if (lp.isEmpty()) lp = null;
    List<YearStatistic> ly = yearStatisticRepository.findAll();
    if (ly.isEmpty()) ly = null;

    List<ProspectionStatus> lps = prospectionStatusRepository.findAll();
    if (lps.isEmpty()) lps = null;

    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    User user = userService.findUserByEmail(auth.getName());
    System.out.println(user.getRoles());
    String userRole = user.getRoles().iterator().next().getRole();
    model.addAttribute("userRole", userRole);

    model.addAttribute("listProducts", lp);
    model.addAttribute("listYears", ly);
    model.addAttribute("listPrStatus", lps);
    model.addAttribute("prospection", new Prospection());
    return "prospections/prospection/add";
  }

  @PostMapping("add")
  //@ResponseBody
  public String addArticle(
    Model model,
    @Valid Prospection prospection,
    @RequestParam(name = "yearId", required = true) Long y,
    @RequestParam(name = "productId", required = true) Long p,
    @RequestParam(name = "prospectStatusQ1Id", required = true) Long psQ1,
    @RequestParam(name = "prospectStatusQ2Id", required = true) Long psQ2,
    @RequestParam(name = "prospectStatusQ3Id", required = true) Long psQ3,
    @RequestParam(name = "prospectStatusQ4Id", required = true) Long psQ4,
    BindingResult result
  ) {
    if (result.hasErrors()) {
      model.addAttribute("prospection", prospection);
      return "prospections/prospection/add";
    }

    Product product = productRepository
      .findById(p)
      .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + p)
      );

    prospection.setProduct(product);

    YearStatistic year = yearStatisticRepository
      .findById(y)
      .orElseThrow(() -> new IllegalArgumentException("Invalid year Id:" + y));

    prospection.setYearStatistic(year);

    ProspectionStatus prospectionStatusQ1 = prospectionStatusRepository
      .findById(psQ1)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid prospection status Id:" + psQ1)
      );

    prospection.setProspectionStatusQ1(prospectionStatusQ1);

    ProspectionStatus prospectionStatusQ2 = prospectionStatusRepository
      .findById(psQ1)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid prospection status Id:" + psQ1)
      );

    prospection.setProspectionStatusQ2(prospectionStatusQ2);

    ProspectionStatus prospectionStatusQ3 = prospectionStatusRepository
      .findById(psQ1)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid prospection status Id:" + psQ1)
      );

    prospection.setProspectionStatusQ3(prospectionStatusQ3);

    ProspectionStatus prospectionStatusQ4 = prospectionStatusRepository
      .findById(psQ1)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid prospection status Id:" + psQ1)
      );

    prospection.setProspectionStatusQ4(prospectionStatusQ4);

    prospectionRepository.save(prospection);

    List<String> statusIds = Arrays.asList(
      "prospection_status_id1",
      "prospection_status_id2",
      "prospection_status_id3",
      "prospection_status_id4"
    );

    conversionRateService.calculateAndSaveConversionRates(statusIds);
    averageDealValueService.calculateAndSaveAverageDeals();
    return "redirect:list";
  }
}
