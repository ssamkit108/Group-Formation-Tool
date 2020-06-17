package com.dal.catmeclone.UserProfile;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DatabaseConnectionImpl;
import com.dal.catmeclone.Validation.ValidationException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

@Controller
public class ForgotPasswordController {

	ForgotPasswordService forgotpasswordservice;

	final Logger logger = LoggerFactory.getLogger(DatabaseConnectionImpl.class);

	@GetMapping("/forgotpassword")
	public String displayforgotpassword(Model model) {
		return "forgotpassword";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ModelAndView processForgotpassword(@RequestParam(name = "username") String bannerID)
			throws SQLException, UserDefinedSQLException {
		try {
			forgotpasswordservice = SystemConfig.instance().getForgotPasswordService();
			if (forgotpasswordservice.ValidateUser(bannerID)) {
				forgotpasswordservice.Resetlink(bannerID);
				ModelAndView m;
				m = new ModelAndView("forgotpassword");
				m.addObject("message", "Password is sent on your registred email address.");
				return m;
			} else {
				ModelAndView m;
				m = new ModelAndView("forgotpassword");
				m.addObject("message", "User does not exist.Please enter the valid BannerID");
				return m;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ModelAndView m = new ModelAndView("forgotpassword");
			m.addObject("message", e.getMessage());
			return m;
		}
	}

	@RequestMapping(value = "/reset", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		try {
			forgotpasswordservice = SystemConfig.instance().getForgotPasswordService();
			String bannerId = forgotpasswordservice.validatetoken(confirmationToken);
			ModelAndView m;
			if (!bannerId.isEmpty() && !bannerId.equals(null)) {
				m = new ModelAndView("ResetPassword");
				m.addObject("bannerid", bannerId);
				return m;
			} else {
				m = new ModelAndView("forgotpassword");
				m.addObject("message", "The link is invalid or broken!");
				return m;

			}
		} catch (Exception e) {
			ModelAndView m;
			m = new ModelAndView("forgotpassword");
			logger.error(e.getLocalizedMessage());
			m.addObject("message", e.getLocalizedMessage());
			return m;
		}
	}

	@RequestMapping(value = "/reset_password", method = RequestMethod.POST)
	public ModelAndView resetPassword(ModelMap model, String bannerId, @RequestParam("password") String password,
			@RequestParam("bannerid") String BannerID, @RequestParam("confirmPassword") String confirmPassword,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			forgotpasswordservice = SystemConfig.instance().getForgotPasswordService();
			ModelAndView m;

			if (password.equals(confirmPassword)) {
				forgotpasswordservice.setNewPassword(BannerID, password);
				logger.info("Password for BannerID:" + BannerID + " has been changed Successfully");
				m = new ModelAndView("login");
				m.addObject("message", "Your password has been changed successfully");
				return m;
			} else {
				m = new ModelAndView("ResetPassword");
				m.addObject("bannerid", BannerID);
				m.addObject("message", "Please enter password and confirm password same.");
				return m;
			}

		} catch (ValidationException e) {
			ModelAndView m = new ModelAndView("ResetPassword");
			m.addObject("bannerid", BannerID);
			m.addObject("message", e.getMessage());
			return m;
		} catch (Exception e) {
			ModelAndView m;
			m = new ModelAndView("ResetPassword");
			m.addObject("bannerid", BannerID);
			m.addObject("message", e.getMessage());
			return m;
		}
	}
}
