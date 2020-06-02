package com.dal.catmeclone.course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;
import com.dal.catmeclone.authenticationandauthorization.Interface_AuthenticateUserDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.model.UserDetails;

@Controller
public class CourseController {

	@GetMapping("/allcourses") 
	public ModelAndView AllCourses(Model model) throws SQLException, UserDefinedSQLException
	{
		Interface_AuthenticateUserDao validate = new AuthenticateUserDao();

		ModelAndView modelview = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated())
		{

			ArrayList<Course> allcourses = validate.getallcourses();			
			modelview = new ModelAndView("/guest_courses");
			modelview.addObject("all_courses",allcourses);
		}
		return modelview;

	}

	@GetMapping("/courses") 
	public ModelAndView Courses(Model model) throws SQLException, UserDefinedSQLException
	{
		Interface_AuthenticateUserDao validate = new AuthenticateUserDao();

		ModelAndView modelview = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated())
		{
			String bannerId = auth.getPrincipal().toString();
			User user = new User();
			user.setBannerId(bannerId);

			ArrayList<UserDetails> user_role = validate.getrolebyuser(user);
			ArrayList<UserDetails> Student_details =  (ArrayList<UserDetails>) user_role.stream().filter(role -> role.getRole_tagged().equals("Student")).collect(Collectors.toList());
			modelview = new ModelAndView("/course_list");
			modelview.addObject("Student_courses",Student_details);
			ArrayList<UserDetails> TA_details =  (ArrayList<UserDetails>) user_role.stream().filter(role -> role.getRole_tagged().equals("TA")).collect(Collectors.toList());
			modelview.addObject("TA_courses",TA_details);
			ArrayList<UserDetails> Instructor_details =  (ArrayList<UserDetails>) user_role.stream().filter(role -> role.getRole_tagged().equals("Instructor")).collect(Collectors.toList());
			modelview.addObject("Instructor_courses",Instructor_details);


		}

		return modelview;

	}
	


}
