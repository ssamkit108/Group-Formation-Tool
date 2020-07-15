package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DatabaseConnectionImpl;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.exceptionhandler.ValidationException;
import com.dal.catmeclone.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
public class SignupController {
    final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectionImpl.class);
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
            ModelAndView m;

            if (user.getPassword().equals(passwordConfirm)) {
                success = userservice.Create(user);
                if (success) {
                    m = new ModelAndView("login");
                    m.addObject("message", "Succesfully created Account.");
                    return m;
                } else {
                    m = new ModelAndView("signup");
                    m.addObject("message", "Sorry,some error generated in creating account.");
                    return m;
                }
            } else {
                m = new ModelAndView("signup");
                m.addObject("message", "Password and confirm password should be same.");
                return m;
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