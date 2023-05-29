package B2B.CRM.dashboard.controllers.accounts;


import B2B.CRM.dashboard.entities.accounts.User;
import B2B.CRM.dashboard.services.acoounts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @RequestMapping(value={"/home"}, method = RequestMethod.GET)
    public ModelAndView accueil(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");

        ///
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println(user.getEmail()+" "+user.getName()+" "+user.getLastName());

        ////

        return modelAndView;
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        boolean successRegister = false;
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        modelAndView.addObject("successRegister", false);
        return modelAndView;
    }
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        boolean successRegister = false;
        if (userExists != null) {

            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");

        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            successRegister = true;
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        modelAndView.addObject("successRegister", successRegister);
        return modelAndView;
    }

    

    @GetMapping("/403")
    public String reddirectError403() {
        return "redirect:../accessDenied";
    }

    @GetMapping("/axessDenied")
    public String error403() {
        return "/error/403";
    }
}



