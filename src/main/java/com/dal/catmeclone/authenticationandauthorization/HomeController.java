package com.dal.catmeclone.authenticationandauthorization;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dal.catmeclone.courses.Course;
import com.dal.catmeclone.courses.CourseService;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;


@Controller
public class HomeController {

	@Autowired
	CourseService courseService;
	
	@GetMapping("/")
    String index(Principal principal) {
        return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
    }
	
	@RequestMapping(value = "/home")
	public String showHome(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		List<Course> listofCourses = new ArrayList<Course>();
		try {
			listofCourses=courseService.getCourseEnrolledForUser(new User(username));
			System.out.println(listofCourses.toString());
			
			if(!listofCourses.isEmpty())
			{
				model.addAttribute("courses",listofCourses);
				model.addAttribute("module","courses");
				model.addAttribute("nocoursemessage","");
			}
			else
			{
				model.addAttribute("nocoursemessage","No Course Registered for you");
				model.addAttribute("module","listallcourses");
			}
			
		} catch (UserDefinedSQLException e) {
			// TODO Auto-generated catch block
			
		}
		
		return "home";
		
	}
	
	
}
