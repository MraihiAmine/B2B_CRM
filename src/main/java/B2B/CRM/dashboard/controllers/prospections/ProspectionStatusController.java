package B2B.CRM.dashboard.controllers.prospections;

import B2B.CRM.dashboard.entities.prospections.ProspectionStatus;
import B2B.CRM.dashboard.repositories.prospections.*;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prospection_status")
public class ProspectionStatusController {

  @Autowired
  private ProspectionStatusRepository prospectionStatusRepository;

  @GetMapping("list")
  public String index(Model model) {
    List<ProspectionStatus> ls = prospectionStatusRepository.findAll();
    if (ls.isEmpty()) ls = null;
    model.addAttribute("prospectionStatusList", ls);
    return "prospections/prospectionStatus/list";
  }

  @GetMapping("/add")
  public String newProspectionStatus(Model model) {
    model.addAttribute("prospectionStatus", new ProspectionStatus());
    return "prospections/prospectionStatus/add";
  }

  @PostMapping("/add")
  public String createProspectionStatus(
    @Valid ProspectionStatus prospectionStatus,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return "prospections/prospectionStatus/add";
    }

    prospectionStatusRepository.save(prospectionStatus);
    return "redirect:list";
  }

  @GetMapping("/edit/{id}")
  public String editProspectionStatus(@PathVariable Long id, Model model) {
    ProspectionStatus prospectionStatus = prospectionStatusRepository
      .findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid ProspectionStatus ID: " + id)
      );

    model.addAttribute("prospectionStatus", prospectionStatus);
    return "prospections/prospectionStatus/update";
  }

  @PostMapping("/edit")
  public String updateProspectionStatus(
    @PathVariable Long id,
    @Valid ProspectionStatus prospectionStatus,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return "prospections/prospectionStatus/update";
    }

    ProspectionStatus existingProspectionStatus = prospectionStatusRepository
      .findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid ProspectionStatus ID: " + id)
      );
    prospectionStatusRepository.save(existingProspectionStatus);
    return "redirect:list";
  }

  @GetMapping("/delete/{id}")
  public String deleteProspectionStatus(@PathVariable Long id) {
    ProspectionStatus prospectionStatus = prospectionStatusRepository
      .findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid prospectionStatus id: " + id)
      );
    prospectionStatusRepository.delete(prospectionStatus);
    return "redirect:/year_statistics/list";
  }
}
