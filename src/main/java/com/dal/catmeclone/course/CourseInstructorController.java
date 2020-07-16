package com.dal.catmeclone.course;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.UserProfile.UserProfileAbstractFactory;
import com.dal.catmeclone.UserProfile.UserService;
import com.dal.catmeclone.exceptionhandler.FileRelatedException;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseInstructorController {


    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    CourseAbstractFactory courseAbstractFactory = abstractFactory.createCourseAbstractFactory();
    UserProfileAbstractFactory userProfileAbstractFactory = abstractFactory.createUserProfileAbstractFactory();
    CourseEnrollmentService courseEnrollmentService;
    UserService userservice;


    @PostMapping(value = "/uploadstudent", consumes = {"multipart/form-data"})
    public String enrollStudents(@RequestParam("file") MultipartFile file, RedirectAttributes attributes,
                                 Model themodel, HttpSession session) {
        courseEnrollmentService = courseAbstractFactory.createCourseEnrollmentService();
        Course course = (Course) session.getAttribute("course");

        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/mycourse/" + course.getCourseID();
        }

        try {
            courseEnrollmentService.enrollStudentForCourse(file, course);
        } catch (FileRelatedException | UserDefinedException e) {
            attributes.addFlashAttribute("message", e.getLocalizedMessage());
            return "redirect:/mycourse/" + course.getCourseID();
        }

        List<String> sucessmessages = courseEnrollmentService.getRecordsSuccessMessage();
        List<String> erromessages = courseEnrollmentService.getRecordsFailureMessage();

        if (sucessmessages.isEmpty()) {
            attributes.addFlashAttribute("message",
                    "Encountered error in the files. Please reverify the records and upload again");
            attributes.addFlashAttribute("errormessages", erromessages);
        } else {
            if (erromessages.isEmpty()) {
                attributes.addFlashAttribute("message", "All the student Records in file are uploaded successfully ");
                attributes.addFlashAttribute("sucessmessages", sucessmessages);

            } else {
                attributes.addFlashAttribute("message",
                        "Records in file are uploadded successfully with " + erromessages.size()
                                + " errors records in the file. Please reverify the error records and upload again");
                attributes.addFlashAttribute("sucessmessages", sucessmessages);
                attributes.addFlashAttribute("errormessages", erromessages);
            }
        }
        return "redirect:/mycourse/" + course.getCourseID();
    }


    @PostMapping(value = "/enrollTA")
    public String enrollTA(@RequestParam String bannerid, RedirectAttributes attributes, Model themodel,
                           HttpSession session) {
        courseEnrollmentService = courseAbstractFactory.createCourseEnrollmentService();
        Course course = (Course) session.getAttribute("course");

        if (courseEnrollmentService.enrollTAForCourse(new User(bannerid), course)) {
            attributes.addFlashAttribute("TAEnrollmessages",
                    "User with " + bannerid + " enrolled successfully as TA for the Course");
        } else {
            attributes.addFlashAttribute("TAEnrollmessages",
                    "Error Occured : Couldn't Enroll User with " + bannerid + " as TA.");
        }

        return "redirect:/mycourse/" + course.getCourseID();
    }


    @GetMapping(value = "/findTA")
    public String enrollStudents(@RequestParam(name = "searchTA") String bannerId, Model themodel,
                                 HttpSession session) {
        userservice = userProfileAbstractFactory.createUserService();
        List<User> listOfUser = new ArrayList<User>();
        try {
            listOfUser = userservice.findAllMatchingUser(bannerId);
            if (listOfUser.isEmpty()) {
                themodel.addAttribute("fetcherror", "No User Found");
            } else {
                themodel.addAttribute("userfetched", listOfUser);
                themodel.addAttribute(new Course());
            }
        } catch (UserDefinedException e) {

            themodel.addAttribute("errormessage", "Please try again later. " + e.getLocalizedMessage());
            return "error";
        }
        return "CI-course";
    }
}
