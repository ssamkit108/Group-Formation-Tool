package com.dal.catmeclone.admin;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Controller
public class AdminController {
	
	CourseManagementDao cd;
	CourseInstructorAssignmentDao ce;
	Role role;
	
	@GetMapping("/courseCreationForm")
	public String getCourseForm(Model m) {
		m.addAttribute("courseCreationForm", new Course());
		return "courseCreationForm";
	}
	
	@PostMapping("/courseCreationForm")
	public String submitCourse(@ModelAttribute Course c, Model m) throws UserDefinedSQLException, SQLException {
	    cd = new CourseManagementDaoImpl();
	    cd.insertCourse(new Course(c.getCourseID(), c.getCourseName()));
		return "saveDetails";
	}
	
	@GetMapping("/adminDashboard")
	public String getAdminDashboard(Model m) throws SQLException, UserDefinedSQLException {
		cd = new CourseManagementDaoImpl();
		m.addAttribute("adminDashboard",new Course());
		m.addAttribute("courses", cd.getAllCourses());
		return "adminDashboard";
	}
	
	@RequestMapping(value= "/adminDashboard", method=RequestMethod.POST, params="action=remove")
		public String remCourse(@ModelAttribute Course c, Model m) throws UserDefinedSQLException, SQLException {
			
			cd = new CourseManagementDaoImpl();
		    cd.deleteCourse(c.getCourseID());
			return "deleteDetails";
		}
	
	@RequestMapping(value= "/adminDashboard", method=RequestMethod.POST, params="action=assign")
	public String assignCourse(@ModelAttribute Course c, Model m) throws UserDefinedSQLException, SQLException {
		
		ce = new CourseInstructorAssignmentDaoImpl();
		cd = new CourseManagementDaoImpl();
		m.addAttribute("assignCourse", new User());
		m.addAttribute("users", ce.getAllUsers());
	    
		//Check if instructor is already instructor for the course
	    if(cd.checkInstructorForCourse(c)) {
	    	return "alreadyAssigned";
	    }
	    
		return "assignInstructor";
}
	
	@PostMapping("/assignInstructor")
	public String enrollInstructor(@ModelAttribute User u,@ModelAttribute Course c,Model m) throws SQLException, UserDefinedSQLException
	{
	    ce = new CourseInstructorAssignmentDaoImpl();   
	    ce.enrollInstructorForCourse(u, c, new Role("Instructor"));
	    return "adminEnrollInstructor";
	}
		
}

