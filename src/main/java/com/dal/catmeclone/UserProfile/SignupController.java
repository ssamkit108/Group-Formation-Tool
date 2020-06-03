package com.dal.catmeclone.UserProfile;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;		
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.encrypt.*;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;


@Controller
public class SignupController
{
	private final String USERNAME = "username";
	private final String PASSWORD = "password";
	private final String confirm_password = "passwordConfirmation";
	private final String FIRST_NAME = "firstName";
	private final String LAST_NAME = "lastName";
	private final String EMAIL = "email";
	
	@Autowired
	private BCryptPasswordEncryption passwordencoder;
	
	@Autowired
	UserService userservice;
	final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

	@GetMapping("/signup")
	public String displaySignup(Model model)
	{
		return "signup";
	}
	
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST) 
   public ModelAndView processSignup(
   	@RequestParam(name = USERNAME) String bannerID,
   	@RequestParam(name = PASSWORD) String password,
   	@RequestParam(name = confirm_password) String passwordConfirm,
   	@RequestParam(name = FIRST_NAME) String firstName,
   	@RequestParam(name = LAST_NAME) String lastName,
   	@RequestParam(name = EMAIL) String email) throws SQLException, UserDefinedSQLException
	{
		boolean success = false;
		try {
		if (password.equals(passwordConfirm))
		{
			User u = new User();
			u.setBannerId(bannerID);
			u.setPassword(passwordencoder.encryptPassword(password));
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setEmail(email);
			success=userservice.Create(u);
		}

		ModelAndView m;
		
		if (success)
		{
			m = new ModelAndView("login");
			m.addObject("message", "Succesfully created Account.");

		}
		else
		{
			m = new ModelAndView("signup");
			m.addObject("message", "Data already exist in the system");
		}
		return m;
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			ModelAndView m=new ModelAndView("error");
			return m;
		}
	}
}