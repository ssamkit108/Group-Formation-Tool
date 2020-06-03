package com.dal.catmeclone.authenticationandauthorization;

import org.springframework.stereotype.Controller	;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dal.catmeclone.model.User;

@Controller
public class AuthenticationController {

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("login", new User());
		return "login";
		
	}
	

	@GetMapping("/") 
	public String Home()
	{
		return "index";

	}
	
}
