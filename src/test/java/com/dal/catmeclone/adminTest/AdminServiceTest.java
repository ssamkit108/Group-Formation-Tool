package com.dal.catmeclone.adminTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.admin.AdminService;
import com.dal.catmeclone.admin.AdminServiceImpl;
import com.dal.catmeclone.admin.CourseInstructorAssignmentDao;
import com.dal.catmeclone.admin.CourseManagementDao;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class AdminServiceTest {

	AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();
	CourseInstructorAssignmentDao courseInstructorAssignmentDaoImpl;
	CourseManagementDao courseManagementDaoImpl;
	UserDao userDao;
    AdminService adminServiceImpl;
    
    public AdminServiceTest() {
    	courseInstructorAssignmentDaoImpl = mock(CourseInstructorAssignmentDao.class);
    	courseManagementDaoImpl = mock(CourseManagementDao.class);
    	userDao = mock(UserDao.class);
    	adminServiceImpl = mock(AdminServiceImpl.class);
    }
    
    @BeforeEach
	public void setup() {
		adminServiceImpl = new AdminServiceImpl(courseInstructorAssignmentDaoImpl, courseManagementDaoImpl, userDao);
    }
    
    @Test
	public void enrollInstructorForCourseTest() throws Exception{
    	Course course = modelfact.createCourse(123);
        User user = modelfact.createUser("B00833211");
        Role role = modelfact.createRole();
        role.setRoleName("Instructor");
		
		when(courseInstructorAssignmentDaoImpl.enrollInstructorForCourse(user, course, role)).thenReturn(true);
		assertTrue(adminServiceImpl.enrollInstructorForCourse(user, course, role));
	}
		
    @Test
    public void getAllUsers() throws Exception {
    	List<User> userList = new ArrayList<User>();
    	User user = modelfact.createUser();
    	user.setBannerId("B00123");
    	userList.add(user);
    	when(userDao.getAllUsers()).thenReturn(userList);
    	assertEquals(adminServiceImpl.getAllUsers(), userList);
    }
	@Test
    public void getAllCourses() throws Exception{
		List<Course> courseList = new ArrayList<Course>();
    	Course course = modelfact.createCourse();
        course.setCourseID(123);
        courseList.add(course);
        when(courseManagementDaoImpl.getAllCourses()).thenReturn(courseList);
        assertEquals(adminServiceImpl.getAllCourses(), courseList);
    }
    
    @Test
    public void deleteCourseTest() throws Exception{
    	Course course = modelfact.createCourse();
        course.setCourseID(123);
		
		when(courseManagementDaoImpl.deleteCourse(course.getCourseID())).thenReturn(true);
		assertTrue(adminServiceImpl.deleteCourse(course.getCourseID()));
    }

    
    @Test
    public void insertCourseTest() throws Exception{
    	Course course = modelfact.createCourse();
        course.setCourseID(123);
		
		when(courseManagementDaoImpl.insertCourse(course)).thenReturn(true);
		assertTrue(adminServiceImpl.insertCourse(course));
    }

    @Test
    public void checkInstructorForCourse() throws Exception{
    	Course course = modelfact.createCourse();
        course.setCourseID(123);
		
		when(courseInstructorAssignmentDaoImpl.checkInstructorForCourse(course)).thenReturn(true);
		assertTrue(adminServiceImpl.checkInstructorForCourse(course));
    }
    
    @Test
    public void checkCourseExists() throws Exception {
    	Course course = modelfact.createCourse();
        course.setCourseID(123);
		
		when(courseManagementDaoImpl.checkCourseExists(course)).thenReturn(true);
		assertTrue(adminServiceImpl.checkCourseExists(course));
    }
}
