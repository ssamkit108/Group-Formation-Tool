package com.dal.catmeclone.forgotpassword;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.notification.*;


@Controller
public class ForgotPasswordController {
	
	private final String USERNAME = "username";
	@Autowired
	ForgotPasswordService forgotpasswordservice;
	
	@GetMapping("/forgotpassword")
	public String displayforgotpassword(Model model)
	{
		return "forgotpassword";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST) 
	public ModelAndView processForgotpassword(
	@RequestParam(name = USERNAME) String bannerID) throws SQLException, UserDefinedSQLException
	{
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
}

