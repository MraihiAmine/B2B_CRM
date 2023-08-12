package B2B.CRM.dashboard.controllers.accounts;
//coment

import B2B.CRM.dashboard.entities.accounts.Role;
import B2B.CRM.dashboard.entities.accounts.User;
import B2B.CRM.dashboard.repositories.accounts.RoleRepository;
import B2B.CRM.dashboard.repositories.accounts.UserRepository;
import B2B.CRM.dashboard.repositories.messages.MessageRepository;
import B2B.CRM.dashboard.services.acoounts.UserService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Getter
@Setter
@AllArgsConstructor
@RequestMapping("/accounts/")
@EnableWebSecurity
public class AccountController {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final MessageRepository messageRepository;

  @Autowired
  private JavaMailSender javaMailSender;

  public static String uploadDirectory =
    System.getProperty("user.dir") + "/src/main/resources/static/uploads_users";

  @Autowired
  private UserService userService;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public AccountController(
    UserRepository userRepository,
    RoleRepository roleRepository,
    MessageRepository messageRepository,
    BCryptPasswordEncoder bCryptPasswordEncoder
  ) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.messageRepository = messageRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @GetMapping("profile")
  public String showProfile(Model model) {
    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    User user = userService.findUserByEmail(auth.getName());
    System.out.println(user.getRoles());
    String userRole = user.getRoles().iterator().next().getRole();
    model.addAttribute("userRole", userRole);
    model.addAttribute("roles", roleRepository.findAll());
    model.addAttribute("user", user);
    return "user/profile";
  }

  @GetMapping("add")
  public String showAddArticleForm(Model model) {
    Authentication auth = SecurityContextHolder
    .getContext()
    .getAuthentication();
    User user = userService.findUserByEmail(auth.getName());
    System.out.println(user.getRoles());
    String userRole = user.getRoles().iterator().next().getRole();
    model.addAttribute("userRole", userRole);
    model.addAttribute("user", new User());
    model.addAttribute("rolesList", roleRepository.findAll());

    return "user/addUser";
  }

  @PostMapping("add")
  //@ResponseBody
  public String addArticle(
    @Valid User user,
    BindingResult result,
    Model model,
    @RequestParam("avtiveValueId") Long activeState,
    @RequestParam("newRole") String newRole
  ) {
    model.addAttribute("rolesList", roleRepository.findAll());
    user.setActive(activeState);
    Role userRole = roleRepository.findByRole(newRole);
    model.addAttribute("userRole", userRole);
    System.out.println("userRole: " + userRole);
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

    user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
    userRepository.save(user);
    return "redirect:list";
  }

  @GetMapping("list")
  public String listUsers(Model model) {
    List<User> users = (List<User>) userRepository.findAll();
    long nbr = userRepository.count();
    if (users.size() == 0) users = null;
    Authentication auth = SecurityContextHolder
    .getContext()
    .getAuthentication();
    User user = userService.findUserByEmail(auth.getName());
    System.out.println(user.getRoles());
    String userRole = user.getRoles().iterator().next().getRole();
    model.addAttribute("userRole", userRole);
    model.addAttribute("users", users);
    model.addAttribute("rolesList", roleRepository.findAll());
    model.addAttribute("nbr", nbr);
    model.addAttribute("showPassword", false);
    return "user/listUsers";
  }

  @GetMapping("enable/{id}/{email}")
  //@ResponseBody
  public String enableUserAcount(
    @PathVariable("id") Long id,
    @PathVariable("email") String email
  ) {
    sendEmail(email, true);
    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
    user.setActive((long) 1);
    userRepository.save(user);
    return "redirect:../../list";
  }

  @GetMapping("disable/{id}/{email}")
  //@ResponseBody
  public String disableUserAcount(
    @PathVariable("id") Long id,
    @PathVariable("email") String email
  ) {
    sendEmail(email, false);

    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
    user.setActive((long) 0);
    userRepository.save(user);
    return "redirect:../../list";
  }

  void sendEmail(String email, boolean state) {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(email);
    if (state) {
      msg.setSubject("Account Has Been Activated");
      msg.setText(
        "Hello, Your account has been activated. " +
        "You can log in http://127.0.0.1:8080/login" +
        " \n Best Regards!"
      );
    } else {
      msg.setSubject("Account Has Been disactivated");
      msg.setText("Hello, Your account has been disactivated.");
    }
    javaMailSender.send(msg);
  }

  @PostMapping("updateRole")
  //@ResponseBody
  public String UpdateUserRole(
    @RequestParam("id") Long id,
    @RequestParam("newrole") String newRole
  ) {
    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));

    Role userRole = roleRepository.findByRole(newRole);

    System.out.println("newRole found for update " + newRole);
    System.out.println("role found for update " + userRole);
    user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

    userRepository.save(user);
    return "redirect:list";
  }

  @GetMapping("/edit/{id}")
  public String editUserForm(
    @PathVariable("id") Long id,
    Model model
  ) {
    model.addAttribute(
      "user",
      userRepository
        .findById(id)
        .orElseThrow(() ->
          new IllegalArgumentException("Invalid user id: " + id)
        )
    );
    Authentication auth = SecurityContextHolder
    .getContext()
    .getAuthentication();
    User user = userService.findUserByEmail(auth.getName());
    System.out.println(user.getRoles());
    String userRole = user.getRoles().iterator().next().getRole();
    model.addAttribute("userRole", userRole);
    List<Role> rolesList = roleRepository.findAll();
    if (rolesList.isEmpty()) rolesList = null;
    model.addAttribute("rolesList", rolesList);
    return "user/updateUser";
  }

  @PostMapping("/edit")
  public String updateProduct(
    @Valid User user,
    @RequestParam("avtiveValueId") Long activeState,
    @RequestParam("roleId") String newRole,
    BindingResult bindingResult,
    Model model
  ) {
    if (bindingResult.hasErrors()) {
      return "user/updateUser";
    }
    List<Role> rolesList = roleRepository.findAll();
    if (rolesList.isEmpty()) rolesList = null;
    model.addAttribute("userRole", newRole);
    model.addAttribute("rolesList", rolesList);
    Role userRole = roleRepository.findByRole(newRole);
    user.setActive(activeState);
    user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return "redirect:list";
  }

  @GetMapping("/delete/{id}")
  public String deleteProduct(@PathVariable("id") Long id) {
    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid users id: " + id)
      );

    user.getRoles().clear();
    userRepository.delete(user);
    return "redirect:../../accounts/list";
  }
}
