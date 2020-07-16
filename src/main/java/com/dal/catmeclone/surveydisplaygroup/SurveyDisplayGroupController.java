package com.dal.catmeclone.surveydisplaygroup;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.surveycreation.CourseAdminSurveyController;

@RequestMapping("/survey/display")
@Controller
public class SurveyDisplayGroupController {

	private Logger LOGGER = Logger.getLogger(CourseAdminSurveyController.class.getName());
	private AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	private SurveyDisplayGroupAbstractFactory surveyDisplayGroupAbstractFactory = abstractFactory.createSurveyDisplayGroupAbstractFactory();
	private ModelAbstractFactory modelAbstractFactory = abstractFactory.createModelAbstractFactory();
	
	
	@PostMapping("/groupinfo")
	private String fetchgroupinfo(@RequestParam int courseid, Model model, RedirectAttributes attributes) {
		SurveyDisplayGroupService surveyDisplayGroupService = surveyDisplayGroupAbstractFactory
				.createSurveyDisplayGroupService();
		Course course = modelAbstractFactory.createCourse();
		course.setCourseID(courseid);
		HashMap<String, List<User>> groupInformation = null;
		try {
			groupInformation = surveyDisplayGroupService.retrievegroupinfo(course.getCourseID());
		} catch (UserDefinedException e) {
			model.addAttribute("errormessage", e.getLocalizedMessage());
			return "error";
		}

		if (groupInformation.isEmpty()) {
			LOGGER.info("Group Information in not avaiable");
			attributes.addFlashAttribute("errormessage", "Groups not yet formed for the course");
			model.addAttribute("nogroupsformed", "Groups are not yet formed");
			model.addAttribute("courseID", courseid);
			return "survey/groupdata";
		} else {
			LOGGER.info("Calling Services to fetch group details");
			model.addAttribute("groups", groupInformation);
			model.addAttribute("courseID", courseid);
			attributes.addFlashAttribute("message", "Fetched group data Successfully.");
			return "survey/groupdata";
		}
	}

	@PostMapping("/viewresponse")
	private String fetchresponse(@RequestParam int courseid, @RequestParam String bannerId, Model model,
			RedirectAttributes attributes) {
		SurveyDisplayGroupService surveyDisplayGroupService = surveyDisplayGroupAbstractFactory
				.createSurveyDisplayGroupService();
		Course course = modelAbstractFactory.createCourse();
		course.setCourseID(courseid);
		HashMap<String, List<Object>> response = new HashMap<>();

		try {
			response = surveyDisplayGroupService.fetchresponse(courseid, bannerId);
		} catch (UserDefinedException e) {
			model.addAttribute("errormessage", e.getLocalizedMessage());
			return "error";
		}
		if (response.isEmpty()) {
			attributes.addFlashAttribute("errormessage", "Responses not given by the student");
			model.addAttribute("noresponsefound", "Response is not submitted");
			return "survey/responsedata";
		} else {
			LOGGER.info("Calling Services to fetch group details");
			model.addAttribute("responses", response);
			attributes.addFlashAttribute("message", "Fetched group data Successfully.");
			return "survey/responsedata";
		}
	}
	
}
