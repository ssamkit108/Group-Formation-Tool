package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.exceptionhandler.ValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.logging.Logger;

@Controller
public class ForgotPasswordController {

    private final Logger LOGGER = Logger.getLogger(ForgotPasswordController.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    UserProfileAbstractFactory userProfileAbstractFactory = abstractFactory.createUserProfileAbstractFactory();
    ForgotPasswordService forgotpasswordservice;

    @GetMapping("/forgotpassword")
    public String displayForgotPassword(Model model) {
        return "forgotpassword";
    }

    @RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
    public ModelAndView processForgotpassword(@RequestParam(name = "username") String bannerID) {
        try {
            forgotpasswordservice = userProfileAbstractFactory.createForgotPasswordService();
            forgotpasswordservice.resetlink(bannerID);
            ModelAndView modelAndView;
            modelAndView = new ModelAndView("forgotpassword");
            modelAndView.addObject("message", "Password is sent on your registred email address.");
            return modelAndView;
        } catch (UserDefinedSQLException e) {
            LOGGER.warning(e.getLocalizedMessage());
            ModelAndView modelAndView = new ModelAndView("forgotpassword");
            modelAndView.addObject("message", e.getLocalizedMessage());
            return modelAndView;
        } catch (Exception e) {
            LOGGER.warning(e.getLocalizedMessage());
            ModelAndView modelAndView = new ModelAndView("forgotpassword");
            modelAndView.addObject("message", e.getLocalizedMessage());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/reset", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
        try {
            forgotpasswordservice = userProfileAbstractFactory.createForgotPasswordService();
            String bannerId = forgotpasswordservice.validateToken(confirmationToken);
            ModelAndView modelAndView1;
            if (!bannerId.isEmpty() && !bannerId.equals(null)) {
                modelAndView1 = new ModelAndView("ResetPassword");
                modelAndView1.addObject("bannerid", bannerId);
                return modelAndView1;
            } else {
                modelAndView1 = new ModelAndView("forgotpassword");
                modelAndView1.addObject("message", "The link is invalid or broken!");
                return modelAndView1;
            }
        } catch (Exception e) {
            ModelAndView modelAndView1;
            modelAndView1 = new ModelAndView("forgotpassword");
            LOGGER.warning(e.getLocalizedMessage());
            modelAndView1.addObject("message", e.getLocalizedMessage());
            return modelAndView1;
        }
    }

    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    public ModelAndView resetPassword(ModelMap model, String bannerId, @RequestParam("password") String password,
                                      @RequestParam("bannerid") String BannerID, @RequestParam("confirmPassword") String confirmPassword,
                                      RedirectAttributes redirectAttributes) throws Exception {
        try {
            forgotpasswordservice = userProfileAbstractFactory.createForgotPasswordService();
            ModelAndView modelAndView;

            if (password.equals(confirmPassword)) {
                forgotpasswordservice.setNewPassword(BannerID, password);

                LOGGER.info("Password for BannerID:" + BannerID + " has been changed Successfully");
                modelAndView = new ModelAndView("login");
                modelAndView.addObject("message", "Your password has been changed successfully");
                return modelAndView;
            } else {
                modelAndView = new ModelAndView("ResetPassword");
                modelAndView.addObject("bannerid", BannerID);
                modelAndView.addObject("message", "Please enter password and confirm password same.");
                return modelAndView;
            }

        } catch (ValidationException e) {
            ModelAndView modelAndView = new ModelAndView("ResetPassword");
            modelAndView.addObject("bannerid", BannerID);
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView;
            modelAndView = new ModelAndView("ResetPassword");
            modelAndView.addObject("bannerid", BannerID);
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        }
    }
}
