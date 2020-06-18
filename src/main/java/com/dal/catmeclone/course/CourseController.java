package com.dal.catmeclone.course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
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
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Controller
public class CourseController {

	CourseService courseService;
	CourseEnrollmentService courseEnrollmentService;

	@GetMapping("mycourse/{courseid}")
	public String showCoursePage(ModelMap model, @PathVariable(name = "courseid") Integer courseid,
			RedirectAttributes attributes, HttpSession session) {

		courseService = SystemConfig.instance().getCourseService();
		courseEnrollmentService = SystemConfig.instance().getCourseEnrollmentService();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		String responsepage = "";

		try {
			Course course = courseService.getCourse(courseid);
			if (course != null) {
				Role role = courseEnrollmentService.getUserRoleForCourse(new User(username), course);

				if (role == null) {
					attributes.addAttribute("InvalidAccessMessage", "Course " + courseid
							+ "doesn't exist in system or access to course " + courseid + " is not provided for you");
					responsepage = "redirect:/access-denied";
				} else if (role.getRoleName().equals("Instructor")) {

					session.setAttribute("role", "Instructor");
					session.setAttribute("course", course);
					responsepage = "CI-course";

				} else if (role.getRoleName().equals("Student")) {

					session.setAttribute("role", "Student");
					session.setAttribute("course", course);
					responsepage = "coursestudentpage";

				} else if (role.getRoleName().equals("TA")) {

					session.setAttribute("role", "TA");
					session.setAttribute("course", course);
					responsepage = "CI-course";
				}
			}

		} catch (UserDefinedSQLException e) {
			attributes.addAttribute("InvalidAccessMessage", "Course " + courseid
					+ "doesn't exist in system or access to course " + courseid + " is not provided");
			return "redirect:/access-denied";
		} catch (CourseException e1) {
			attributes.addAttribute("InvalidAccessMessage " + courseid + "doesn't exist in system or access to course "
					+ courseid + " is not provided");
			responsepage = "redirect:/access-denied";
		}

		return responsepage;
	}

	@GetMapping("/allcourses")
	public ModelAndView AllCourses(Model model) {
		courseService = SystemConfig.instance().getCourseService();
		ModelAndView modelview = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			ArrayList<Course> allcourses = null;
			try {
				allcourses = courseService.getallcourses();
			} catch (SQLException e) {

				modelview = new ModelAndView("error");
				model.addAttribute("errormessage", "Some Error occured");
			} catch (UserDefinedSQLException e) {
				modelview = new ModelAndView("error");
				model.addAttribute("errormessage", "Some Error occured");

			}
			modelview = new ModelAndView("guest_courses");
			if (!allcourses.isEmpty()) {
				modelview.addObject("all_courses", allcourses);
			} else {
				modelview.addObject("nocoursemessage", "No Courses available in the System");
			}

		}
		return modelview;

	}

	@GetMapping("/courses")
	public ModelAndView Courses(Model model, HttpSession session) {

		courseService = SystemConfig.instance().getCourseService();
		courseEnrollmentService = SystemConfig.instance().getCourseEnrollmentService();
		// Interface_AuthenticateUserDao validate = new AuthenticateUserDao();
		ModelAndView modelview = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		session.removeAttribute("course");
		List<Course> listofCourses = new ArrayList<Course>();
		try {
			listofCourses = courseEnrollmentService.getCourseEnrolledForUser(new User(username));

			if (!listofCourses.isEmpty()) {
				modelview = new ModelAndView("course_list");
				modelview.addObject("courses", listofCourses);

				session.setAttribute("enrolled", true);
				modelview.addObject("nocoursemessage", "");
			} else {
				modelview = new ModelAndView("guest_courses");
				session.setAttribute("enrolled", false);
				ArrayList<Course> allcourses = null;
				try {
					allcourses = courseService.getallcourses();
					if (!allcourses.isEmpty()) {
						modelview.addObject("all_courses", allcourses);
					} else {
						modelview.addObject("nocoursemessage", "No Courses available in the System");
					}
				} catch (SQLException e) {
					modelview = new ModelAndView("error");
					model.addAttribute("errormessage", "Some Error occured");
				} catch (UserDefinedSQLException e) {
					modelview = new ModelAndView("error");
					model.addAttribute("errormessage", "Some Error occured");
				}

			}

		} catch (UserDefinedSQLException e) {
			modelview = new ModelAndView("error");
			model.addAttribute("errormessage", "Some Error occured");

		}
		return modelview;
	}
}
