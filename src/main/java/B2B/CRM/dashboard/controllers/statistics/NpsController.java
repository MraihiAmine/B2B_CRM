package B2B.CRM.dashboard.controllers.statistics;

import B2B.CRM.dashboard.entities.statistics.NpsEntity;
import B2B.CRM.dashboard.entities.statistics.SalesGrowthRateEntity;
import B2B.CRM.dashboard.repositories.statisitics.NpsRepository;
import B2B.CRM.dashboard.services.statistics.NpsEntityService;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/nps")
public class NpsController {

  private final NpsRepository npsRepository;

  @Autowired
  public NpsController(NpsRepository npsRepository) {
    this.npsRepository = npsRepository;
  }

  @Autowired
  private NpsEntityService npsEntityService;

  @GetMapping("add")
  public ModelAndView showAddArticleForm(Model model) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("nps", new NpsEntity());
    modelAndView.setViewName("statistics/nps/addNps");
    return modelAndView;
  }

  @PostMapping("add")
  public ModelAndView addArticle(
    @Valid NpsEntity npsEntity,
    BindingResult result
  ) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("nps", npsEntity);
    if (result.hasErrors()) {
      modelAndView.setViewName("statistics/nps/addNps");
      System.out.println("result: " + result);
      return modelAndView;
    }
    npsRepository.save(npsEntity);
    modelAndView.setViewName("statistics/dashboard");
    return modelAndView;
  }

  @GetMapping("list")
  public ModelAndView listProviders() {
      List<NpsEntity> la = npsRepository.findAll();
    ModelAndView modelAndView = new ModelAndView();
    if (la.isEmpty()) la = null;
    modelAndView.addObject("npsList", la);
    modelAndView.setViewName("statistics/nps/listNps");
    return modelAndView;
  }



  @PutMapping("/{id}")
  public ResponseEntity<NpsEntity> update(
    @PathVariable Long id,
    @RequestBody NpsEntity npsEntity
  ) {
    if (!npsRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }
    npsEntity.setId(id);
    NpsEntity savedEntity = npsRepository.save(npsEntity);
    return ResponseEntity.ok(savedEntity);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!npsRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }
    npsRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
