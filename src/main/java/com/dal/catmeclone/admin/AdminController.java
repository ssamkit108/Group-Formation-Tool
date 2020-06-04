package com.dal.catmeclone.admin;

import java.sql.SQLException;	
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
	
	@GetMapping("/courseCreationForm")
	public String getCourseForm(Model m) {
		m.addAttribute("courseCreationForm", new Course());
		return "admin/courseCreationForm";
	}
	
	@PostMapping("/courseCreationForm")
	public String submitCourse(@ModelAttribute Course c, Model m) {
	    
	    //If course already exists
		try {
			if(adminService.checkCourseExists(c)) {
		    	return "admin/courseExists";
		    }
		    } catch (SQLException | UserDefinedSQLException e) {
		    	logger.error("Some Sql exception caught in admin controller");
				m.addAttribute("errormessage",e.getLocalizedMessage());
				return "error";
			}
		try {
		adminService.insertCourse(new Course(c.getCourseID(), c.getCourseName()));
	    } catch (SQLException | UserDefinedSQLException e) {
	    	logger.error("Some Sql exception caught in admin controller");
			m.addAttribute("errormessage",e.getLocalizedMessage());
			return "error";
		}
		return "admin/saveDetails";
	}
	
	@GetMapping("/adminDashboard")
	public String getAdminDashboard(Model m) {
		
		m.addAttribute("adminDashboard",new Course());
		try {
		m.addAttribute("courses", adminService.getAllCourses());
		} catch (SQLException | UserDefinedSQLException e) {
			logger.error("Some Sql exception caught in admin controller");
			m.addAttribute("errormessage",e.getLocalizedMessage());
			return "error";
		}
		return "admin/adminDashboard";
	}
	
	@RequestMapping(value= "/adminDashboard", method=RequestMethod.POST, params="action=remove")
		public String remCourse(@ModelAttribute Course c, Model m)  {
			
		    try {
				adminService.deleteCourse(c.getCourseID());
			} catch (SQLException | UserDefinedSQLException e) {
				logger.error("Some Sql exception caught in admin controller");
				m.addAttribute("errormessage",e.getLocalizedMessage());
				return "error";
			}
			return "admin/deleteDetails";
		}
	
	@RequestMapping(value= "/adminDashboard", method=RequestMethod.POST, params="action=assign")
	public String assignCourse(@ModelAttribute Course c, Model m) {
		
		
		m.addAttribute("assignCourse", new User());
		try {
		m.addAttribute("users", adminService.getAllUsers());
		} catch (SQLException | UserDefinedSQLException e) {
			logger.error("Some Sql exception caught in admin controller");
			m.addAttribute("errormessage",e.getLocalizedMessage());
			return "error";
		}
		
		//Check if instructor is already instructor for the course
	    try {
		if(adminService.checkInstructorForCourse(c)) {
	    	return "admin/alreadyAssigned";
	    }
	    } catch (SQLException | UserDefinedSQLException e) {
	    	logger.error("Some Sql exception caught in admin controller");
			m.addAttribute("errormessage",e.getLocalizedMessage());
			return "error";
		}
		return "admin/assignInstructor";
}
	
	@PostMapping("/assignInstructor")
	public String enrollInstructor(@ModelAttribute User u,@ModelAttribute Course c,Model m)
	{  
	    try {
		adminService.enrollInstructorForCourse(u, c, new Role("Instructor"));
	    } catch (SQLException | UserDefinedSQLException e) {
	    	logger.error("Some Sql exception caught in admin controller");
			m.addAttribute("errormessage",e.getLocalizedMessage());
			return "error";
		}
	    
		return "admin/adminEnrollInstructor";
	}
	
}

