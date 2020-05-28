package com.dal.catmeclone.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dal.catmeclone.model.User;

@Controller
public class UserController {

	@GetMapping("/login")
	public String login_form(Model model) {
		model.addAttribute("login", new User());
		return "login";
		
	}
}
