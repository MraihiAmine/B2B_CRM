package B2B.CRM.dashboard.controllers.accounts;

import B2B.CRM.dashboard.entities.accounts.Role;
import B2B.CRM.dashboard.entities.accounts.User;
import B2B.CRM.dashboard.repositories.accounts.RoleRepository;
import B2B.CRM.dashboard.services.acoounts.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/role/")
public class RoleController {

  private final RoleRepository roleRepository;

  @Autowired
  private UserService userService;

  @Autowired
  public RoleController(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @GetMapping("list")
  public String listRoles(Model model) {
    List<Role> roles = (List<Role>) roleRepository.findAll();
    if (roles.size() == 0) roles = null;
    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    User user = userService.findUserByEmail(auth.getName());
    System.out.println(user.getRoles());
    String userRole = user.getRoles().iterator().next().getRole();
    model.addAttribute("userRole", userRole);
    model.addAttribute("rolesList", roles);
    return "role/list";
  }

  @GetMapping("add")
  public String showAddRoleForm(Model model) {
    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    User user = userService.findUserByEmail(auth.getName());
    System.out.println(user.getRoles());
    String userRole = user.getRoles().iterator().next().getRole();
    model.addAttribute("userRole", userRole);
    model.addAttribute("role", new Role());
    return "role/add";
  }

  @PostMapping("add")
  public String addRole(@RequestParam("role") String role, Model model) {
    System.out.println(role);
    Role r = new Role();
    r.setRole(role);
    Role rSaved = roleRepository.save(r);
    System.out.println("role = " + rSaved);
    model.addAttribute("userRole", role);
    return "redirect:list";
  }

  @GetMapping("/edit/{id}")
  public String editRole(@PathVariable Integer id, Model model) {
    Role role = roleRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid Role ID: " + id)
      );

    System.out.println("found role: " + role);

    model.addAttribute("userRole", role.getRole());
    model.addAttribute("role", role);
    return "role/update";
  }

  @PostMapping("/edit")
  public String updateRole(
    @RequestParam("role") String role,
    @RequestParam("id") Integer id,
    Model model
  ) {
    Role r = roleRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid Role ID: " + id)
      );
    r.setRole(role);
    model.addAttribute("userRole", role);
    Role rSaved = roleRepository.save(r);
    System.out.println("role = " + rSaved);
    return "redirect:list";
  }

  @GetMapping("/delete/{id}")
  public String deleteRole(@PathVariable Integer id) {
    Role role = roleRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid role id: " + id)
      );
    roleRepository.delete(role);
    return "redirect:../list";
  }
}
