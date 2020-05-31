package com.dal.catmeclone;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import com.dal.catmeclone.dao.CourseDao;
import com.dal.catmeclone.dao.CourseDaoImpl;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

@Controller
public class AdminAddCourseController {
 
	CourseDao cd;
	
	@GetMapping("/courseCreationForm")
	public String getCourseForm(Model m) {
		m.addAttribute("courseCreationForm", new Course());
		return "courseCreationForm";
	}
	
	@PostMapping("/courseCreationForm")
	public String submitCourse(@ModelAttribute Course c, Model m) throws UserDefinedSQLException, SQLException {
	    cd = new CourseDaoImpl();
	    cd.insertCourse(new Course(c.getCourseID(), c.getCourseName()));
		return "saveDetails";
	}
}
