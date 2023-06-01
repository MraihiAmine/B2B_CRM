package B2B.CRM.dashboard.controllers.statistics;

import B2B.CRM.dashboard.entities.statistics.Product;
import B2B.CRM.dashboard.repositories.statisitics.ProductRepository;
import B2B.CRM.dashboard.services.acoounts.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product/")
public class ProductController {

  @Autowired
  private UserService userService;

  @Autowired
  private ProductRepository productRepository;

  @GetMapping("list")
  public String showProducts(Model model) {
    List<Product> la = productRepository.findAll();
    if (la.isEmpty()) la = null;
    String userRole = userService.getConnectedUserRole();
    model.addAttribute("userRole", userRole);
    model.addAttribute("productList", la);
    return "statistics/product/list";
  }

  @GetMapping("add")
  public String newProductForm(Model model) {
    String userRole = userService.getConnectedUserRole();
    model.addAttribute("userRole", userRole);
    model.addAttribute("product", new Product());
    return "statistics/product/add";
  }

  @PostMapping("add")
  public String saveProduct(
    @Valid Product product,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return "statistics/product/add";
    }
    productRepository.save(product);
    return "redirect:list";
  }

  @GetMapping("/edit/{id}")
  public String editProductForm(@PathVariable("id") Long id, Model model) {
    model.addAttribute(
      "product",
      productRepository
        .findById(id)
        .orElseThrow(() ->
          new IllegalArgumentException("Invalid product id: " + id)
        )
    );
    String userRole = userService.getConnectedUserRole();
    model.addAttribute("userRole", userRole);
    return "statistics/product/update";
  }

  @PostMapping("/edit")
  public String updateProduct(
    @Valid Product product,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return "statistics/product/update";
    }
    productRepository.save(product);
    return "redirect:list";
  }

  @GetMapping("/delete/{id}")
  public String deleteProduct(@PathVariable("id") Long id) {
    Product product = productRepository
      .findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid product id: " + id)
      );
    productRepository.delete(product);
    return "redirect:/product/list";
  }
}
