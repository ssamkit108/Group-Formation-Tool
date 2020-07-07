package com.dal.catmeclone.surveyresponse;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.questionmanagement.QuestionManagementController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Controller
public class ResponseController {

    private Logger LOGGER = Logger.getLogger(ResponseController.class.getName());
    AbstractFactory abstractFactory= SystemConfig.instance().getAbstractFactory();
    SurveyResponseAbstractFactory surveyResponseAbstractFactory=abstractFactory.createSurveyResponseAbstractFactory();
    ModelAbstractFactory modelAbstractFactory=abstractFactory.createModelAbstractFactory();

    @GetMapping(value = "/responsepage/{courseid}")
    public String viewResponsePage(Model model,@PathVariable(name = "courseid") Integer courseid) {
        ResponseService responseService=surveyResponseAbstractFactory.createResponseService();
        UserSurveyResponse userSurveyResponse=modelAbstractFactory.createUserSurveyResponse();
        Course course=modelAbstractFactory.crateCourse();
        course.setCourseID(courseid);
        User user=modelAbstractFactory.createUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try{
            LOGGER.info("Fetching the list of questions for Displaying on Student survey page");
            user.setBannerId(username);
            Set<SurveyQuestionResponse> surveyQuestionResponses=new HashSet<SurveyQuestionResponse>(responseService.getAllQuestion(courseid));
            userSurveyResponse.setUser(user);
            userSurveyResponse.setSurveyResponse(surveyQuestionResponses);
            model.addAttribute("surveyQuestions", userSurveyResponse);

        }catch (UserDefinedSQLException e){
            LOGGER.warning(e.getLocalizedMessage());
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        catch (Exception e) {
            LOGGER.warning(e.getLocalizedMessage());
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "fillsurvey";
    }
}
