package com.dal.catmeclone.courses;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Controller
@RequestMapping("/mycourse")
public class CourseController {
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	CourseEnrollmentService courseEnrollmentService;
	
	@GetMapping("/{courseid}")
	public String showCoursePage(ModelMap model,@PathVariable(name = "courseid") Integer courseid, RedirectAttributes attributes, HttpSession session)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		String responsepage ="";
		System.out.println(courseid);
		try {
			Course course = courseService.getCourse(courseid);
			if(course!=null) {
				Role r= courseService.getUserRoleForCourse(new User(username), course);
				System.out.println(r+","+course);
				if(r==null)
				{
					attributes.addAttribute("InvalidAccessMessage","Course "+courseid+"doesn't exist in system or access to course "+courseid+" is not provided");
					responsepage=  "redirect:/";
				}
				else if(r.getRoleName().equals("Instructor"))
				{	
					
					List<User> listOfTA= courseEnrollmentService.getTAForCourse(new Course(courseid));
					System.out.println(listOfTA.toString());
					System.out.println("setting session attribute"+course.getCourseID());
					session.setAttribute("course", course);
					model.addAttribute("course",course);
					model.addAttribute("listOfTa",listOfTA);
					
					responsepage=  "CI-course";
				}
				else if(r.getRoleName().equals("Student"))
				{
					responsepage= "redirect:/";
				}
				else if(r.getRoleName().equals("TA"))
				{
					responsepage= "redirect:/";
				}
			}
			
			
		} catch (UserDefinedSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			attributes.addAttribute("InvalidAccessMessage","Course "+courseid+"doesn't exist in system or access to course "+courseid+" is not provided");
			return "redirect:/";
		} catch (CourseException e1) {
			// TODO Auto-generated catch block
			attributes.addAttribute("Course "+courseid+"doesn't exist in system or access to course "+courseid+" is not provided");
			responsepage=  "redirect:/";
		}
		
		
		return responsepage;
	}
	
	

}
