package authenticationtest;

import java.util.ArrayList;

import com.dal.catmeclone.authenticationandauthorization.Interface_AuthenticateUserDao;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class AuthenticationDaoMock implements Interface_AuthenticateUserDao{

	private String bannerId;
	private String password;
	private int courseid;
	private String courseName;
	private int role_id;
	private String role_tagged;
	
	public AuthenticationDaoMock() {
		default_data();
	}
	
	public void default_data() {
		bannerId = "B00832190";
		password = "password";
		courseid = 123;
		courseName = "Web";
		role_id = 1;
		role_tagged = "Student";
		
		
	}
	@Override
	public ArrayList<Course> getallcoursesbyuser(User user){
		Course crc = new Course();
		user.setBannerId(user.getBannerId());
		crc.setCourseID(courseid);
		crc.setCourseName(courseName);
		
		ArrayList<Course> crlst = new ArrayList<Course>();
		crlst.add(crc);
		
		return crlst;
	}


	@Override
	public ArrayList<Course> getallcourses()  {
		Course crc = new Course();
		crc.setCourseID(courseid);
		crc.setCourseName(courseName);
		
		ArrayList<Course> crlst = new ArrayList<Course>();
		crlst.add(crc);
		return crlst;
	}

	@Override
	public User authenticateUser(User user) {
		user.setBannerId(bannerId);
		user.setPassword(password);
		Role rl = new Role();
		rl.setRoleId(role_id);
		rl.setRoleName(role_tagged);
		user.setUserRoles(rl);		
		return user;
	}



	
}
