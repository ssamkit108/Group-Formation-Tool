package com.dal.catmeclone.admin;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import org.apache.logging.log4j.message.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.logging.Logger;

@Controller
public class AdminController {

    final Logger LOGGER = Logger.getLogger(AdminController.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    AdminAbstractFactory adminAbstractFactory = abstractFactory.createAdminAbstractFactory();
    ModelAbstractFactory modelAbstractFactory = abstractFactory.createModelAbstractFactory();
    AdminService adminService = adminAbstractFactory.createAdminService();

    @GetMapping("/admin/courseCreationForm")
    public String getCourseForm(Model model) {
        model.addAttribute("courseCreationForm", modelAbstractFactory.crateCourse());
        return "admin/courseCreationForm";
    }

    @PostMapping("/admin/courseCreationForm")
    public String submitCourse(@ModelAttribute Course course, Model model, @ModelAttribute("message") Message message,
                               BindingResult bindingResult) throws Exception {
        try {
            if (adminService.checkCourseExists(course)) {
                model.addAttribute("courseexists", "Course already exists");
                model.addAttribute("courseCreationForm", modelAbstractFactory.crateCourse());
                return "admin/courseCreationForm";
            }
        } catch (SQLException | UserDefinedSQLException e) {
            LOGGER.warning("Some Sql exception caught in admin controller");
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        try {
            adminService.insertCourse(new Course(course.getCourseID(), course.getCourseName()));
        } catch (SQLException | UserDefinedSQLException e) {
            LOGGER.warning("Some Sql exception caught in admin controller");
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "admin/saveDetails";
    }

    @GetMapping("/admin/adminDashboard")
    public String getAdminDashboard(Model model) {

        model.addAttribute("adminDashboard", new Course());
        try {
            model.addAttribute("courses", adminService.getAllCourses());
        } catch (SQLException | UserDefinedSQLException e) {
            LOGGER.warning("Some Sql exception caught in admin controller");
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        } catch (Exception e) {
            LOGGER.warning("Some Generic exception caught in admin controller");
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "admin/adminDashboard";
    }

    @RequestMapping(value = "/admin/adminDashboard", method = RequestMethod.POST, params = "action=remove")
    public String remCourse(@ModelAttribute Course course, Model model) {

        try {
            adminService.deleteCourse(course.getCourseID());
        } catch (SQLException | UserDefinedSQLException e) {
            LOGGER.warning("Some Sql exception caught in admin controller");
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        } catch (Exception e) {
            LOGGER.warning("Some Generic exception caught in admin controller");
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "admin/deleteDetails";
    }

    @RequestMapping(value = "/admin/adminDashboard", method = RequestMethod.POST, params = "action=assign")
    public String assignCourse(@ModelAttribute Course course, Model model) throws Exception {

        model.addAttribute("assignCourse", new User());
        try {
            model.addAttribute("users", adminService.getAllUsers());
        } catch (SQLException | UserDefinedSQLException e) {
            LOGGER.warning("Some Sql exception caught in admin controller");
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        try {
            if (adminService.checkInstructorForCourse(course)) {
                model.addAttribute("instructorassigned", "Instructor is already assigned");
                model.addAttribute("adminDashboard", new Course());
                model.addAttribute("courses", adminService.getAllCourses());
                return "admin/adminDashboard";
            }
        } catch (SQLException | UserDefinedSQLException e) {
            LOGGER.warning("Some Sql exception caught in admin controller");
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "admin/assignInstructor";
    }

    @PostMapping("/admin/assignInstructor")
    public String enrollInstructor(@ModelAttribute User user, @ModelAttribute Course course, Model model) {

        try {
            adminService.enrollInstructorForCourse(user, course, new Role("Instructor"));
        } catch (SQLException | UserDefinedSQLException e) {
            LOGGER.warning("Some Sql exception caught in admin controller");
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        } catch (Exception e) {
            LOGGER.warning("Some Generic exception caught in admin controller");
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "admin/adminEnrollInstructor";
    }
}
