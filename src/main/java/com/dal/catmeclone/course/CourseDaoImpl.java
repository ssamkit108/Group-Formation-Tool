package com.dal.catmeclone.course;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Component
public class CourseDaoImpl implements CoursesDao {
	
	
	@Autowired
	private DatabaseConnection DBUtil;
	
	
	
	@Value("${procedure.getCourse}")
	private String getCourse;
	
	private Connection connection;
	
	

	@Override
	public Course getCourse(int courseId) throws UserDefinedSQLException,CourseException {
		// TODO Auto-generated method stub
		Course course=null;
		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();
			
			statement = connection.prepareCall("{call "+getCourse+"}");
		
			statement.setInt(1, courseId);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				course = new Course(rs.getInt(1), rs.getString(2));
			}
			
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CourseException("Error Occured: Course doesn't exist with given Id");
			
		} finally {
			DBUtil.terminateStatement(statement);
			if (connection != null) {	
					DBUtil.terminateConnection();
			}
		}
		return course;
	}

}
