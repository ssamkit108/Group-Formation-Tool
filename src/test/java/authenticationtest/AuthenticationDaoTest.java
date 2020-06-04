package authenticationtest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

@SpringBootTest
public class AuthenticationDaoTest {

	AuthenticationDaoMock dbmock = new AuthenticationDaoMock();
	
	
	@Test
	void getallcoursesbyuserTest() {
		User user = new User();
		user.setBannerId("B00832190");
		Course crc = new Course();
		crc.setCourseID(123);
		crc.setCourseName("Web");
		ArrayList<Course> crlst = new ArrayList<Course>();
		crlst.add(crc);		
		assertArrayEquals(crlst.toArray(),dbmock.getallcoursesbyuser(user).toArray());
	}
	
	@Test
	void getallcoursesTest() {
		
		Course crc = new Course();
		crc.setCourseID(123);
		crc.setCourseName("Web");
		ArrayList<Course> crlst = new ArrayList<Course>();
		crlst.add(crc);	
		assertArrayEquals(crlst.toArray(),dbmock.getallcourses().toArray());
	}
	
	
	@Test
	void authenticateUserTest() {
		User u = new User();
		User usr = dbmock.authenticateUser(u);
		assertEquals("B00832190",usr.getBannerId());
		assertEquals("Student",usr.getUserRoles().roleName);
		assertEquals("password",usr.getPassword());

		
	}
}
