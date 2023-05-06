package B2B.CRM.dashboard.controllers.accounts;


import B2B.CRM.dashboard.entities.accounts.Role;
import B2B.CRM.dashboard.entities.accounts.User;
import B2B.CRM.dashboard.repositories.accounts.RoleRepository;
import B2B.CRM.dashboard.repositories.accounts.UserRepository;
import B2B.CRM.dashboard.repositories.messages.MessageRepository;
import B2B.CRM.dashboard.services.acoounts.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


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
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads_users";

    @Autowired
    private UserService userService;


    @Autowired
    public AccountController(UserRepository userRepository, RoleRepository roleRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("add")
    public String showAddArticleForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());

        return "user/addUser";
    }

    @GetMapping("profile")
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println(user.getEmail()+" "+user.getName()+" "+user.getLastName());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("add")
    //@ResponseBody
    public String addArticle(@Valid User user, BindingResult result,
                             @RequestParam("files") MultipartFile[] files
    ) {
        MultipartFile file = files[0];
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());

        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Stockage du name du ficher dans la base
        StringBuilder fileName = new StringBuilder();
        fileName.append(file.getOriginalFilename());
        user.setPicture(fileName.toString());


        userService.saveUser(user);
        return "redirect:list";

        //return article.getLabel() + " " +article.getPrice() + " " + p.toString();
    }

    @GetMapping("list")
    public String listUsers(Model model) {

        List<User> users = (List<User>) userRepository.findAll();
        long nbr = userRepository.count();
        if (users.size() == 0)
            users = null;
        model.addAttribute("users", users);
        model.addAttribute("nbr", nbr);
        model.addAttribute("showPassword", false);
        return "user/listUsers";
    }

    @GetMapping("enable/{id}/{email}")
    //@ResponseBody
    public String enableUserAcount(@PathVariable("id") int id,
                                   @PathVariable("email") String email) {

        sendEmail(email, true);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
        user.setActive(1);
        userRepository.save(user);
        return "redirect:../../list";
    }

    @GetMapping("disable/{id}/{email}")
    //@ResponseBody
    public String disableUserAcount(@PathVariable("id") int id,
                                    @PathVariable("email") String email) {

        sendEmail(email, false);

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
        user.setActive(0);
        userRepository.save(user);
        return "redirect:../../list";
    }

    void sendEmail(String email, boolean state) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        if (state) {
            msg.setSubject("Account Has Been Activated");
            msg.setText("Hello, Your account has been activated. "
                    +
                    "You can log in http://127.0.0.1:8080/login"
                    + " \n Best Regards!");
        } else {
            msg.setSubject("Account Has Been disactivated");
            msg.setText("Hello, Your account has been disactivated.");
        }
        javaMailSender.send(msg);

    }


    @PostMapping("updateRole")
    //@ResponseBody
    public String UpdateUserRole(@RequestParam("id") int id,
                                 @RequestParam("newrole") String newRole
    ) {

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));

        Role userRole = roleRepository.findByRole(newRole);

        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

        userRepository.save(user);
        return "redirect:list";
    }


}
