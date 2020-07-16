package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.exceptionhandler.ValidationException;
import com.dal.catmeclone.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.logging.Logger;

@Controller
public class SignupController {

    private final Logger LOGGER = Logger.getLogger(SignupController.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    UserProfileAbstractFactory userProfileAbstractFactory = abstractFactory.createUserProfileAbstractFactory();
    private UserService userservice;

    @GetMapping("/signup")
    public String displaySignup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView processSignup(ModelMap model, User user,
                                      @RequestParam(name = "passwordConfirmation") String passwordConfirm, RedirectAttributes redirectAttributes)
            throws SQLException, ValidationException, UserDefinedSQLException {
        boolean success = false;
        userservice = userProfileAbstractFactory.createUserService();

        try {
            ModelAndView modelAndView;

            if (user.getPassword().equals(passwordConfirm)) {
                success = userservice.createUser(user);
                if (success) {
                    modelAndView = new ModelAndView("login");
                    modelAndView.addObject("message", "Succesfully created Account.");
                    return modelAndView;
                } else {
                    modelAndView = new ModelAndView("signup");
                    modelAndView.addObject("message", "Sorry,some error generated in creating account.");
                    return modelAndView;
                }
            } else {
                modelAndView = new ModelAndView("signup");
                modelAndView.addObject("message", "Password and confirm password should be same.");
                return modelAndView;
            }
        } catch (DuplicateEntityException e) {
            ModelAndView m = new ModelAndView("signup");
            m.addObject("message", e.getMessage());
            return m;
        } catch (UserDefinedSQLException e) {
            ModelAndView m = new ModelAndView("signup");
            m.addObject("message", e.getMessage());
            return m;
        } catch (ValidationException e) {
            ModelAndView m = new ModelAndView("signup");
            m.addObject("message", e.getMessage());
            return m;
        } catch (Exception e) {
            ModelAndView m = new ModelAndView("signup");
            m.addObject("message", e.getLocalizedMessage());
            return m;
        }
    }
}