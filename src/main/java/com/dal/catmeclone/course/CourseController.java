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
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Controller
public class CourseController {

	@Autowired
	CourseService courseService;

	@Autowired
	CourseEnrollmentService courseEnrollmentService;
	
	

	@GetMapping("/mycourse/{courseid}")
	public String showCoursePage(ModelMap model, @PathVariable(name = "courseid") Integer courseid,
			RedirectAttributes attributes, HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		
		String responsepage = "";
		System.out.println(courseid);
		try {
			Course course = courseService.getCourse(courseid);
			if (course != null) {
				Role r = courseEnrollmentService.getUserRoleForCourse(new User(username), course);
				System.out.println(r + "," + course);
				if (r == null) {
					attributes.addAttribute("InvalidAccessMessage", "Course " + courseid
							+ "doesn't exist in system or access to course " + courseid + " is not provided");
					responsepage = "redirect:/";
				} else if (r.getRoleName().equals("Instructor")) {

					//List<User> listOfTA = courseEnrollmentService.getTAForCourse(new Course(courseid));
					//System.out.println(listOfTA.toString());
					//System.out.println("setting session attribute" + course.getCourseID());
					session.setAttribute("course", course);
					//model.addAttribute("course", course);
					//model.addAttribute("listOfTa", listOfTA);

					responsepage = "CI-course";
				} else if (r.getRoleName().equals("Student")) {
					session.setAttribute("course", course);
					responsepage = "coursestudentpage";
				} else if (r.getRoleName().equals("TA")) {
					responsepage = "redirect:/";
				}
			}

		} catch (UserDefinedSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			attributes.addAttribute("InvalidAccessMessage", "Course " + courseid
					+ "doesn't exist in system or access to course " + courseid + " is not provided");
			return "redirect:/";
		} catch (CourseException e1) {
			// TODO Auto-generated catch block
			attributes.addAttribute("Course " + courseid + "doesn't exist in system or access to course " + courseid
					+ " is not provided");
			responsepage = "redirect:/";
		}

		return responsepage;
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
			listofCourses=courseEnrollmentService.getCourseEnrolledForUser(new User(username));
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
