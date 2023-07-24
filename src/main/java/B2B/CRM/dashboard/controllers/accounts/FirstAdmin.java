package B2B.CRM.dashboard.controllers.accounts;

import B2B.CRM.dashboard.entities.accounts.Role;
import B2B.CRM.dashboard.entities.accounts.User;
import B2B.CRM.dashboard.repositories.accounts.RoleRepository;
import B2B.CRM.dashboard.repositories.accounts.UserRepository;
import B2B.CRM.dashboard.services.acoounts.UserService;
import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/")
public class FirstAdmin {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  UserService userService;

  @Autowired
  public FirstAdmin(
    RoleRepository roleRepository,
    UserService userService,
    UserRepository userRepository,
    BCryptPasswordEncoder bCryptPasswordEncoder
  ) {
    this.roleRepository = roleRepository;
    this.userService = userService;
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @RequestMapping("/firstAdmin")
  @ResponseBody
  public String addUser() {
    //comment 24 07 2023
    System.out.println("add user was called");
    User user = new User();
    user.setEmail("mraihiamin@gmail.com");
    user.setPassword(bCryptPasswordEncoder.encode("123456"));
    user.setName("Mraihi");
    user.setLastName("Amine");
    user.setActive((long)1);
    Role userRole = new Role();
    userRole.setRole("SUPERADMIN");
    roleRepository.save(userRole);

    Role userRole2 = roleRepository.findByRole("SUPERADMIN");

    user.setRoles(new HashSet<Role>(Arrays.asList(userRole2)));

    userRepository.save(user);
    return "Added";
  }
}
