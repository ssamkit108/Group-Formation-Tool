package com.dal.catmeclone.coursesTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.course.CourseEnrollmentDao;
import com.dal.catmeclone.course.CourseEnrollmentService;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class CourseEnrollmentDaoTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelAbstractFactory = abstractFactoryTest.createModelAbstractFactory();

    CourseEnrollmentDao courseEnrollmentDaoMock;
    UserDao userDaoMock;
    CourseEnrollmentService courseEnrollmentService;

    @BeforeEach
    public void set() {
        courseEnrollmentDaoMock = abstractFactoryTest.createCourseAbstractFactory().createCourseEnrollmentDao();
        userDaoMock = abstractFactoryTest.createUserProfileAbstractFactory().createUserDao();
        courseEnrollmentService = abstractFactoryTest.createCourseAbstractFactory().createCourseEnrollmentService(userDaoMock, courseEnrollmentDaoMock);
    }


    @Test
    public void enrollTAForCourseExisting() throws UserDefinedException {

        Course course = modelAbstractFactory.createCourse();
        course.setCourseID(100);
        course.setCourseName("CourseA");

        User student = modelAbstractFactory.createUser();
        student.setBannerId("B00000001");

        Role role = modelAbstractFactory.createRole();
        role.setRoleName("TA");

        Assert.isTrue(courseEnrollmentService.enrollTAForCourse(student, course), "Passed: User already exist");
    }

    @Test
    public void enrollTAForCourseNotExisting() throws UserDefinedException {

        Course course = modelAbstractFactory.createCourse();
        course.setCourseID(100);
        course.setCourseName("CourseA");

        User student = modelAbstractFactory.createUser();
        student.setBannerId("B00546789");

        Role role = modelAbstractFactory.createRole();
        role.setRoleName("TA");

        Assert.isTrue(courseEnrollmentService.enrollTAForCourse(student, course), "Passed: User registered");
    }

    @Test
    public void getAllEnrolledCourse() throws UserDefinedException {
        User user = modelAbstractFactory.createUser();
        user.setBannerId("B00000001");
        Assert.notEmpty(courseEnrollmentService.getCourseEnrolledForUser(user), "Passed: User have enrolled courses");
    }

    @Test
    public void getAllEnrolledCourseNoCourseEnrolled() throws UserDefinedException {
        User user = modelAbstractFactory.createUser();
        user.setBannerId("B00000001");
        Assert.isTrue(courseEnrollmentService.getCourseEnrolledForUser(user).size() != 0, "Passed: User don't any have enrolled courses");
    }

    @Test
    public void getUserRoleForCourse() throws UserDefinedException {
        User user = modelAbstractFactory.createUser();
        user.setBannerId("B00000001");
        Course course = modelAbstractFactory.createCourse();
        course.setCourseID(100);
        course.setCourseName("CourseA");
        Assert.notNull(courseEnrollmentService.getUserRoleForCourse(user, course), "Passed: User have a role tagged to the course");
    }

    @Test
    public void getUserRoleForCourseUserNotEnrolledInCourse() throws UserDefinedException {
        User user = modelAbstractFactory.createUser();
        user.setBannerId("B00000009");
        Course course = modelAbstractFactory.createCourse();
        course.setCourseID(100);
        course.setCourseName("CourseA");
        Assert.isNull(courseEnrollmentService.getUserRoleForCourse(user, course), "Passed: User don't have any role tagged to the course");
    }

    @Test
    public void enrollUserForCourseNonExisting() throws UserDefinedException {


        Course course = modelAbstractFactory.createCourse();
        course.setCourseID(100);
        course.setCourseName("CourseA");

        User student = modelAbstractFactory.createUser();
        student.setBannerId("B00000007");

        Role role = modelAbstractFactory.createRole();
        role.setRoleName("Student");
        Assert.isTrue(courseEnrollmentDaoMock.enrollUserForCourse(student, course, role), "Passed: User Enrolled Successfully");
    }

}
