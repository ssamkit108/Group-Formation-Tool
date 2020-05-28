package com.dal.catmeclone.controller;

import java.sql.SQLException;	

import org.springframework.stereotype.Controller;		
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.dal.catmeclone.CreateUser.*;
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
	private BCryptPasswordEncryption passwordencoder=new BCryptPasswordEncryption();
    private Interface_CreateUser userservice=new CreateUser();
	
    @GetMapping("/login")
	public String login_form(Model model) {
		model.addAttribute("login", new User());
		return "login";
    }
    
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
		if (User.isBannerIDValid(bannerID) &&
			 User.isEmailValid(email) &&
			 User.isFirstNameValid(firstName) &&
			 User.isLastNameValid(lastName) &&
			 password.equals(passwordConfirm))
		{
			User u = new User();
			u.setBannerId(bannerID);
			u.setPassword(passwordencoder.encryptPassword(password));
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setEmail(email);
			success=userservice.createUser(u);
		}
		ModelAndView m;
		if (success)
		{
			m = new ModelAndView("login");
		}
		else
		{
			m = new ModelAndView("signup");
			m.addObject("error", "Invalid data");
		}
		return m;
	}
}