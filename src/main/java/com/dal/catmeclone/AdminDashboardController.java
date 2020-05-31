package com.dal.catmeclone;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dal.catmeclone.dao.CourseDao;
import com.dal.catmeclone.dao.CourseDaoImpl;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

@Controller
public class AdminDashboardController {
	
	CourseDao cd;
	
	@GetMapping("/adminDashboard")
	public String getAdminDashboard(Model m) throws SQLException, UserDefinedSQLException {
		cd = new CourseDaoImpl();
		m.addAttribute("adminDashboard",new Course());
		m.addAttribute("courses", cd.getAllCourses());
		return "adminDashboard";
	}
	
	@PostMapping("/adminDashboard")
	public String remCourse(@ModelAttribute Course c, Model m) throws UserDefinedSQLException, SQLException {
	    cd = new CourseDaoImpl();
	    cd.deleteCourse(c.getCourseID());
		return "deleteDetails";
	}
}

