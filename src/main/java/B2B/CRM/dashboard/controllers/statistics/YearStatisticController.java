package B2B.CRM.dashboard.controllers.statistics;

import B2B.CRM.dashboard.entities.statistics.YearStatistic;
import B2B.CRM.dashboard.repositories.statisitics.YearStatisticRepository;
import B2B.CRM.dashboard.services.acoounts.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/year_statistics")
public class YearStatisticController {

  @Autowired
  private YearStatisticRepository yearStatisticRepository;

  @Autowired
  private UserService userService;

  @GetMapping("list")
  public String index(Model model) {
    List<YearStatistic> ls = yearStatisticRepository.findAll();
    if (ls.isEmpty()) ls = null;
    String userRole = userService.getConnectedUserRole();
    model.addAttribute("userRole", userRole);
    model.addAttribute("yearStatisticsList", ls);
    return "statistics/yearStatistic/list";
  }

  @GetMapping("/add")
  public String newYearStatistic(Model model) {
    String userRole = userService.getConnectedUserRole();
    model.addAttribute("userRole", userRole);
    model.addAttribute("yearStatistic", new YearStatistic());
    return "statistics/yearStatistic/add";
  }

  @PostMapping("/add")
  public String createYearStatistic(
    @Valid YearStatistic yearStatistic,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return "statistics/yearStatistic/add";
    }

    yearStatisticRepository.save(yearStatistic);
    return "redirect:list";
  }

  @GetMapping("/edit/{id}")
  public String editYearStatistic(@PathVariable Long id, Model model) {
    YearStatistic yearStatistic = yearStatisticRepository
      .findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid YearStatistic ID: " + id)
      );

    String userRole = userService.getConnectedUserRole();
    model.addAttribute("userRole", userRole);
    model.addAttribute("yearStatistic", yearStatistic);
    return "statistics/yearStatistic/update";
  }

  @PostMapping("/edit")
  public String updateYearStatistic(
    @PathVariable Long id,
    @Valid YearStatistic yearStatistic,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return "statistics/yearStatistic/update";
    }

    YearStatistic existingYearStatistic = yearStatisticRepository
      .findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid YearStatistic ID: " + id)
      );
    yearStatisticRepository.save(existingYearStatistic);
    return "redirect:list";
  }

  @GetMapping("/delete/{id}")
  public String deleteYearStatistic(@PathVariable Long id) {
    YearStatistic yearStatistic = yearStatisticRepository
      .findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid yearStatistic id: " + id)
      );
    yearStatisticRepository.delete(yearStatistic);
    return "redirect:/year_statistics/list";
  }
}
