package com.dal.catmeclone.UserProfile;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;



@Controller
public class ForgotPasswordController {
	
	@Autowired
	ForgotPasswordService forgotpasswordservice;
	
	final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

	@GetMapping("/forgotpassword")
	public String displayforgotpassword(Model model)
	{
		return "forgotpassword";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST) 
	public ModelAndView processForgotpassword(
	@RequestParam(name = "username") String bannerID) throws SQLException, UserDefinedSQLException
	{
	try {
	if(forgotpasswordservice.forgotpassword(bannerID)) {
		ModelAndView m;
		m = new ModelAndView("forgotpassword");
		m.addObject("message", "Password is sent on your registred email address.");
		return m;
	}
	else {
		ModelAndView m;
		m = new ModelAndView("forgotpassword");
		m.addObject("message", "Please enter the valid BannerID");
		return m;
	}
	}
	catch(Exception e) {
		logger.error(e.getLocalizedMessage());
		ModelAndView m = new ModelAndView("error");
		return m;
	}
	
	
	}
}

