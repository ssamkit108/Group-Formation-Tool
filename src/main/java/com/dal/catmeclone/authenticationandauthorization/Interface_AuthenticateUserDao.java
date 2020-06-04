package com.dal.catmeclone.authenticationandauthorization;

import java.sql.SQLException;	
import java.util.ArrayList;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;



public interface Interface_AuthenticateUserDao {
	public ArrayList<Course> getallcoursesbyuser(User user)throws UserDefinedSQLException;

	public User authenticateUser(User user) throws SQLException, UserDefinedSQLException;

	public ArrayList<Course> getallcourses()throws SQLException, UserDefinedSQLException;

}
