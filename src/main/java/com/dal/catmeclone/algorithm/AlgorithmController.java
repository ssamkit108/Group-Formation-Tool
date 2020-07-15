package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Properties;
import java.util.logging.Logger;

@Controller
public class AlgorithmController {

    private final Logger logger = Logger.getLogger(AlgorithmController.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    AlgorithmAbstractFactory algorithmAbstractFactory = abstractFactory.createAlgorithmAbstractFactory();
    Properties properties = SystemConfig.instance().getProperties();

    @GetMapping("algorithm")
    public String runAlgorithm(Model model, HttpSession session, RedirectAttributes attributes) {
        Boolean result=false;
        try {
            logger.info("Running the group formation algorithm for Displaying on View");

            GroupFormationStrategy groupFormationStrategy = algorithmAbstractFactory.createGroupFormationStrategy(properties.getProperty("StrategyName"));
            AlgorithmContext algorithmContext = algorithmAbstractFactory.createAlgorithmContext(groupFormationStrategy);
            Course course=(Course) session.getAttribute("course");
            result=algorithmContext.executeStrategy(course);
            model.addAttribute("courseID",course.getCourseID());
            if(result){
                logger.info("Groups created successfully.");
            }
            else{
                logger.info("Groups not created successfully.");
            }
        } catch (UserDefinedSQLException e) {
            logger.warning(e.getLocalizedMessage());
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        } catch (Exception e) {
            logger.warning(e.getLocalizedMessage());
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "survey/groupdata";
    }
}
