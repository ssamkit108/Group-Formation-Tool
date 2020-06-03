package authenticationtest;

import java.sql.SQLException;
import java.util.ArrayList;

import com.dal.catmeclone.authenticationandauthorization.Interface_AuthenticateUserDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.model.UserDetails;

public class AuthenticationDaoMock implements Interface_AuthenticateUserDao{

	@Override
	public ArrayList<Course> getallcoursesbyuser(User user) throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getbyBannerId(User user) throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Course> getallcourses() throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<UserDetails> getrolebyuser(User user) throws UserDefinedSQLException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
