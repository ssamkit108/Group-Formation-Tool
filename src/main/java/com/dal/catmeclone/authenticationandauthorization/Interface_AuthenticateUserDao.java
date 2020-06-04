package com.dal.catmeclone.authenticationandauthorization;

import java.sql.SQLException;	
import java.util.ArrayList;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

import com.dal.catmeclone.model.UserDetails;

public interface Interface_AuthenticateUserDao {
	public ArrayList<Course> getallcoursesbyuser(User user)throws UserDefinedSQLException;

	public User authenticateUser(User user) throws SQLException, UserDefinedSQLException;

	public ArrayList<Course> getallcourses()throws SQLException, UserDefinedSQLException;

}
