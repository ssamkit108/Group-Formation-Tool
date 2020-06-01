package com.dal.catmeclone.useraccess;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.dal.catmeclone.courses.Course;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

@Controller
public class UserController {
	
	@Autowired
	UserService userservice;
	
	@GetMapping(value = "/finduser")
	public String enrollStudents(@RequestParam(name = "searchTA") String bannerId, Model themodel)
	{
		//System.out.println("here at controller");
		List<User> listOfUser= new ArrayList<User>();
		try {
			listOfUser=userservice.findAllMatchingUser(bannerId);
			if(!listOfUser.isEmpty())
			{
				themodel.addAttribute("userfetched",listOfUser);
				themodel.addAttribute(new Course());
			}
			else {
				themodel.addAttribute("fetcherror","No User Found");
			}
		} catch (UserDefinedSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "CI-course";
	}

}
