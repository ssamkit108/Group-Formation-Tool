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
   	@RequestParam(name="username") String bannerID,
   	@RequestParam(name ="password") String password,
   	@RequestParam(name = "passwordConfirmation") String passwordConfirm,
   	@RequestParam(name = "firstName") String firstName,
   	@RequestParam(name = "lastName") String lastName,
   	@RequestParam(name = "email") String email) throws SQLException, UserDefinedSQLException
	{
		boolean success = false;

		try {
			ModelAndView m;

		if (password.equals(passwordConfirm))
		{
			User u = new User();
			u.setBannerId(bannerID);
			u.setPassword(passwordencoder.encryptPassword(password));
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setEmail(email);
			success=userservice.Create(u);
			if (success)
			{
				m = new ModelAndView("login");
				m.addObject("message", "Succesfully created Account.");
				return m;

			}
			else
			{
				m = new ModelAndView("signup");
				m.addObject("message", "Sorry,some error generated in creating account.");
				return m;

			}
		}
		else {
			m = new ModelAndView("signup");
			m.addObject("message", "Password and confirm password should be same.");
			return m;

		}
		
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			ModelAndView m=new ModelAndView("error");
			return m;
		}
	}
}