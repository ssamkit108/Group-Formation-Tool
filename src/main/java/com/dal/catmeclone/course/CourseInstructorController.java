package com.dal.catmeclone.course;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dal.catmeclone.exceptionhandler.FileRelatedException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

@Controller
public class CourseInstructorController {

	@Autowired
	CourseEnrollmentService courseenrollmentservice;
	
	@Autowired
	com.dal.catmeclone.UserProfile.UserService userservice;

	@PostMapping(value = "/uploadstudent", consumes = { "multipart/form-data" })
	public String enrollStudents(@RequestParam("file") MultipartFile file, RedirectAttributes attributes,
			 Model themodel, HttpSession session) {

		Course course = (Course) session.getAttribute("course");
		
		if (file.isEmpty()) {
			attributes.addFlashAttribute("message", "Please select a file to upload.");
			return "redirect:/mycourse/" + course.getCourseID();
		}

		try {
			courseenrollmentservice.enrollStudentForCourse(file, course);
		} catch (FileRelatedException e) {
			// TODO Auto-generated catch block
			attributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/mycourse/" + course.getCourseID();
		}
		List<String> sucessmessages = courseenrollmentservice.getRecordsSuccessMessage();
		List<String> erromessages = courseenrollmentservice.getRecordsFailureMessage();
		
		if (!sucessmessages.isEmpty()) {
			if (!erromessages.isEmpty()) {
				attributes.addFlashAttribute("message",
						"Records in file are uploadded successfully with " + erromessages.size()
								+ " errors records in the file. Please reverify the error records and upload again");
				attributes.addFlashAttribute("sucessmessages", sucessmessages);
				attributes.addFlashAttribute("errormessages", erromessages);
			} else {
				attributes.addFlashAttribute("message", "All the student Records in file are uploaded successfully ");
				attributes.addFlashAttribute("sucessmessages", sucessmessages);
			}
		} else {
			attributes.addFlashAttribute("message",
					"Encountered error in the files. Please reverify the records and upload again");
			attributes.addFlashAttribute("errormessages", erromessages);
		}
		

		return "redirect:/mycourse/" + course.getCourseID();
	}

	@PostMapping(value = "/enrollTA")
	public String enrollTA(@RequestParam String bannerid, RedirectAttributes attributes, Model themodel,
			HttpSession session) {

		Course course = (Course) session.getAttribute("course");

		if (courseenrollmentservice.enrollTAForCourse(new User(bannerid), course)) {
			attributes.addFlashAttribute("TAEnrollmessages",
					"User with " + bannerid + " enrolled successfully as TA for the Course");
		} else {
			attributes.addFlashAttribute("TAEnrollmessages",
					"Error Occured : Couldn't Enroll User with " + bannerid + " as TA.");
		}

		return "redirect:/mycourse/" + course.getCourseID();
	}
	
	@GetMapping(value = "/findTA")
	public String enrollStudents(@RequestParam(name = "searchTA") String bannerId, Model themodel, HttpSession session) {
		Course cs = (Course) session.getAttribute("course");
		System.out.println(cs.getCourseID());
		List<User> listOfUser = new ArrayList<User>();
		try {
			listOfUser = userservice.findAllMatchingUser(bannerId);
			if (!listOfUser.isEmpty()) {
				themodel.addAttribute("userfetched", listOfUser);
				themodel.addAttribute(new Course());
			} else {
				themodel.addAttribute("fetcherror", "No User Found");
			}
		} catch (UserDefinedSQLException e) {

			themodel.addAttribute("errormessage","Some Error occured.Please try again later");
			return "error";
		}
		return "CI-course";
	}
	
	
	
	

}
