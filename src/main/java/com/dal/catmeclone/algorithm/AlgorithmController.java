package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.logging.Logger;
import com.dal.catmeclone.model.Course;

import javax.servlet.http.HttpSession;

@Controller
public class AlgorithmController {

	private final Logger LOGGER = Logger.getLogger(AlgorithmController.class.getName());
	AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	AlgorithmAbstractFactory algorithmAbstractFactory = abstractFactory.createAlgorithmAbstractFactory();

	@GetMapping("algorithm")
	public String runAlgorithm(Model model, HttpSession session) {

		AlgorithmService algorithmService = algorithmAbstractFactory.createAlgorithmService();

		try {
			LOGGER.info("Running the group formation algorithm for Displaying on View");
			System.out.println("Group Formation");
			
			// Calling group formation algorithm
			System.out.println(algorithmService.groupFormation((Course)session.getAttribute("course")));

		} catch (UserDefinedSQLException e) {
			// Handling Error occurred by throwing to Error view with user defined error
			// message.
			model.addAttribute("errormessage", e.getLocalizedMessage());
			return "error";
		}
		return "showgroups";
	}
}
