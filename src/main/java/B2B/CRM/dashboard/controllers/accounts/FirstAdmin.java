package B2B.CRM.dashboard.controllers.accounts;


import B2B.CRM.dashboard.entities.accounts.Role;
import B2B.CRM.dashboard.entities.accounts.User;
import B2B.CRM.dashboard.repositories.accounts.RoleRepository;
import B2B.CRM.dashboard.services.acoounts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class FirstAdmin {
    private final RoleRepository roleRepository;
    UserService userService;
    @Autowired
    public FirstAdmin(RoleRepository roleRepository, UserService userService)
    {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }
    @RequestMapping("/add")
    @ResponseBody
    public String addUser()
    {
        System.out.println("add user was called");
        User user = new User();
        Role role = new Role();
        role.setRole("SUPERADMIN");
        roleRepository.save(role);
        user.setEmail("mraihiamin@gmail.com");
        user.setPassword("123456");
        user.setName("Amine");
        user.setLastName("MRAIHI");
        user.setRoles(new HashSet<Role>(List.of(role)));
        this.userService.saveUser(user);
        return "Added";
    }
}
