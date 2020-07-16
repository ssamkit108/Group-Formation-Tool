package com.dal.catmeclone.authenticationandauthorization;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
public class AuthenticationController {

    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    ModelAbstractFactory modelAbstractFactory = abstractFactory.createModelAbstractFactory();
    private Logger LOGGER = Logger.getLogger(AuthenticationController.class.getName());

    @RequestMapping("/login")
    public String login(Model model) {
        User user = modelAbstractFactory.createUser();
        model.addAttribute("login", user);
        return "login";
    }

    @RequestMapping("/home")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            LOGGER.info("getting user role of logged in user");
            String userole = authentication.getAuthorities().toArray()[0].toString();
            if (userole.equalsIgnoreCase("Admin")) {
                LOGGER.info("Redirecting Admin to Admin Dashboard");
                return "redirect:/adminDashboard";
            } else {
                LOGGER.info("Redirecting user to course page");
                return "redirect:/courses";
            }
        } else {
            return "home";
        }
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

    @RequestMapping("/")
    public String Home() {
        return "index";
    }

}
