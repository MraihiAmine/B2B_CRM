package B2B.CRM.dashboard.services.acoounts;

import B2B.CRM.dashboard.entities.accounts.Role;
import B2B.CRM.dashboard.entities.accounts.User;
import B2B.CRM.dashboard.repositories.accounts.RoleRepository;
import B2B.CRM.dashboard.repositories.accounts.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserService(
    UserRepository userRepository,
    RoleRepository roleRepository,
    BCryptPasswordEncoder bCryptPasswordEncoder
  ) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public void saveUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setActive((long) 0);
    Role userRole = roleRepository.findByRole("USER");
    user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
    userRepository.save(user);
  }

  public String getConnectedUserRole() {
    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    User user = findUserByEmail(auth.getName());
    System.out.println(user.getRoles());
    String userRole = user.getRoles().iterator().next().getRole();
    return userRole;
  }
}
