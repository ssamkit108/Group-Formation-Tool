package com.dal.catmeclone.surveyresponse;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.logging.Logger;

@Controller
public class ResponseController {

    private final Logger LOGGER = Logger.getLogger(ResponseController.class.getName());
    AbstractFactory abstractFactory= SystemConfig.instance().getAbstractFactory();
    SurveyResponseAbstractFactory surveyResponseAbstractFactory=abstractFactory.createSurveyResponseAbstractFactory();
    ModelAbstractFactory modelAbstractFactory=abstractFactory.createModelAbstractFactory();


    @GetMapping(value = "/responsepage/{courseid}")
    public String viewResponsePage(Model model,@PathVariable(name = "courseid") Integer courseid) {
        ResponseService responseService=surveyResponseAbstractFactory.createResponseService();
        UserSurveyResponse userSurveyResponse=modelAbstractFactory.createUserSurveyResponse();
        User user=modelAbstractFactory.createUser();
        Survey survey=modelAbstractFactory.createSurvey();
        Course course=modelAbstractFactory.crateCourse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        user.setBannerId(username);
        course.setCourseID(courseid);
        survey.setCourse(course);
        try{
            if(responseService.checkSubmitted(username,courseid)){
                LOGGER.warning(username+" already submitted survey for the course "+courseid+" .");
                model.addAttribute("errormessage",username+" already submitted survey for the course "+courseid+" .");
                return "coursestudentpage";
            }
            else if(responseService.checkPublished(courseid)){
                LOGGER.info("Fetching the list of questions for Displaying on Student survey page");
                userSurveyResponse.setUser(user);
                userSurveyResponse.setSurvey(survey);
                userSurveyResponse.setSurveyResponse(responseService.getAllQuestion(courseid));
                model.addAttribute("surveyQuestions", userSurveyResponse);
                return "fillsurvey";
            }
            else{
                LOGGER.warning("survey is not published.");
                model.addAttribute("errormessage","Survey is not published yet.");
                return "coursestudentpage";
            }

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
    }

    @RequestMapping(value= "/responsepage/submit",method= RequestMethod.POST)
    public String submitSurvey(@ModelAttribute("surveyQuestions") UserSurveyResponse userSurveyResponse,
                               Model model,RedirectAttributes attributes) {
        try{
            userSurveyResponse.setSubmitted(true);
            Date responseDate= new Date(System.currentTimeMillis());
            userSurveyResponse.setResponseDate(responseDate);
            User user=modelAbstractFactory.createUser();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            user.setBannerId(username);
            userSurveyResponse.setUser(user);
            ResponseService responseService=surveyResponseAbstractFactory.createResponseService();
            LOGGER.info("creating " + userSurveyResponse + " question");
            responseService.setAllresponses(userSurveyResponse);
            return "coursestudentpage";
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
    }
}
