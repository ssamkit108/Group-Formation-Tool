package com.dal.catmeclone.course;

import java.sql.SQLException;	
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Controller
public class CourseController {


	
	@Autowired
	AuthenticateUserDao authenticateUserDB;

	
	@GetMapping("/allcourses") 
	public ModelAndView AllCourses(Model model) 
	{
		//Interface_AuthenticateUserDao validate = new AuthenticateUserDao();

		ModelAndView modelview = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated())
		{

			ArrayList<Course> allcourses = null;
			try {
				allcourses = authenticateUserDB.getallcourses();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserDefinedSQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			modelview = new ModelAndView("/guest_courses");
			if(!allcourses.isEmpty())
			{
				modelview.addObject("all_courses",allcourses);
			}
			else
			{
				modelview.addObject("nocoursemessage","No Courses available in the System");
			}
			
		}
		return modelview;

	}

	@GetMapping("/courses") 
	public ModelAndView Courses(Model model) 
	{
		//Interface_AuthenticateUserDao validate = new AuthenticateUserDao();
		ModelAndView modelview = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		List<Course> listofCourses = new ArrayList<Course>();
		try {
			listofCourses=authenticateUserDB.getallcoursesbyuser(new User(username));
			System.out.println(listofCourses.toString());
			
			if(!listofCourses.isEmpty())
			{
				modelview = new ModelAndView("/course_list");
				modelview.addObject("courses",listofCourses);
				//modelview.addObject("module","courses");
				modelview.addObject("nocoursemessage","");
			}
			else
			{
				modelview = new ModelAndView("/course_list");
				model.addAttribute("nocoursemessage","No Course Registered for you");
				//model.addAttribute("module","listallcourses");
			}
			
		} catch (UserDefinedSQLException e) {
			// TODO Auto-generated catch block
			modelview = new ModelAndView("/error");
			model.addAttribute("errormessage","Some Error occured");
			
		}
		
		return modelview;
		
		

	}
	

}
