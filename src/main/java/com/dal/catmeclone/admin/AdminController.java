package com.dal.catmeclone.admin;

import java.sql.SQLException;

import org.apache.logging.log4j.message.Message;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DatabaseConnectionImpl;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Controller
public class AdminController {
	
	
	AdminService adminService = SystemConfig.instance().getAdminService();
	
	
	final Logger logger = LoggerFactory.getLogger(DatabaseConnectionImpl.class);
	
	@GetMapping("/admin/courseCreationForm")
	public String getCourseForm(Model m) {
		
		m.addAttribute("courseCreationForm", new Course());
		return "admin/courseCreationForm";
	}
	
	@PostMapping("/admin/courseCreationForm")
	public String submitCourse(@ModelAttribute Course c, Model m, @ModelAttribute("message") Message message, BindingResult bindingResult) {
	    
		
	    //If course already exists
		try {
			if(adminService.checkCourseExists(c)) {
				m.addAttribute("courseexists","Course already exists");
				m.addAttribute("courseCreationForm", new Course());
		    	return "admin/courseCreationForm";
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
	
	@GetMapping("/admin/adminDashboard")
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
	
	@RequestMapping(value= "/admin/adminDashboard", method=RequestMethod.POST, params="action=remove")
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
	
	@RequestMapping(value= "/admin/adminDashboard", method=RequestMethod.POST, params="action=assign")
	public String assignCourse(@ModelAttribute Course c, Model m) {
		
		m.addAttribute("assignCourse", new User());
		try {
		m.addAttribute("users", adminService.getAllUsers());
		} catch (SQLException | UserDefinedSQLException e) {
			logger.error("Some Sql exception caught in admin controller");
			m.addAttribute("errormessage",e.getLocalizedMessage());
			return "error";
		}
		
		//Check if instructor is already assigned for the course
	    try {
		if(adminService.checkInstructorForCourse(c)) {
			m.addAttribute("instructorassigned","Instructor is already assigned");
			m.addAttribute("adminDashboard",new Course());
			m.addAttribute("courses", adminService.getAllCourses());
	    	return "admin/adminDashboard";
	    }
	    } catch (SQLException | UserDefinedSQLException e) {
	    	logger.error("Some Sql exception caught in admin controller");
			m.addAttribute("errormessage",e.getLocalizedMessage());
			return "error";
		}
		return "admin/assignInstructor";
}
	
	@PostMapping("/admin/assignInstructor")
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

