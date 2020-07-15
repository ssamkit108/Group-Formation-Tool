package com.dal.catmeclone.authenticationandauthorization;

import com.dal.catmeclone.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationController {

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new User());
        return "login";
    }

    @RequestMapping("/home")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String userole = authentication.getAuthorities().toArray()[0].toString();
            if (userole.equalsIgnoreCase("Admin")) {
                return "redirect:/adminDashboard";
            } else {
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
