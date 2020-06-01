/**
 * 
 */
package com.dal.catmeclone.courses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.notification.NotificationService;
import com.dal.catmeclone.useraccess.UserDao;

/**
 * @author Mayank
 *
 */
@Service
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {
	
	public List<String> recordsSuccessMessage = new ArrayList<String>();
	public List<String> recordsFailureMessage = new ArrayList<String>();
	
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	UserDao userDB;
	
	@Autowired
	CourseEnrollmentDao courseEnrollDB;
	
	@Autowired
	CoursesDao courseDB;
	
	
	public List<String> getRecordsSuccessMessage() {
		return recordsSuccessMessage;
	}


	public List<String> getRecordsFailureMessage() {
		return recordsFailureMessage;
	}

	
	/*
	 * function to iterate data over the student list and make a call to enroll student one by one.
	 */
	@Override
	public boolean enrollStudentForCourse(MultipartFile file, Course course) {
		// TODO Auto-generated method stub
		recordsSuccessMessage = new ArrayList<String>();
		recordsFailureMessage = new ArrayList<String>();
		Set<User> usersToBeEnrolled = new HashSet<User>();
		usersToBeEnrolled = loadDataFromCSV(file);

		Iterator<User> listIterator = usersToBeEnrolled.iterator();
		while (listIterator.hasNext()) {
			User student = listIterator.next();
			try {
				enrollStudent(student, course);
			} catch (UserDefinedSQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	

	/*
	 * function to load the data from given csv file into Set of student objects.
	 */
	private Set<User> loadDataFromCSV(MultipartFile file) {
		Set<User> usersToBeEnrolled = new HashSet<User>();
		BufferedReader br = null;
		try {

			br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String line = br.readLine();
			int count =1;
			while ((line = br.readLine()) != null) {

				// considering comma as separator for csv
				String[] details = line.split(",");
				if (details.length == 4) {
					User student = new User(details[0], details[1], details[2], details[3]);
					usersToBeEnrolled.add(student);
				} else {
					recordsFailureMessage.add("Details provided at row "+(count+1)+" are not valid");
				}
				count++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return usersToBeEnrolled;
	}
	
	/*
	 * function to check if user exists and enroll or create user accordingly
	 */
	private void enrollStudent(User user, Course course) throws UserDefinedSQLException
	{
		User u=userDB.findUserByBannerID(user.getBannerId());
		if(null!=u)
		{
			if(!courseEnrollDB.hasEnrolledInCourse(user.getBannerId(), course.getCourseID()))
			{
				Role role= new Role("Student");
				courseEnrollDB.enrollUserForCourse(user, course, role);
				recordsSuccessMessage.add("User with BannerId: "+user.getBannerId()+" enroll sucessfully as student to the course");
			}
			else
			{
				recordsFailureMessage.add("User with "+user.getBannerId()+" already enrolled in the course");
			}
		}
		else
		{
			user.setPassword("Password123");
			boolean isCreated = userDB.createUser(user);
			if(isCreated)
			{
				Role role= new Role("Student");
				courseEnrollDB.enrollUserForCourse(user, course, role);
				notificationService.sendNotificationToNewuser(user, course);
				recordsSuccessMessage.add("User with BannerId: "+user.getBannerId()+" created and enroll sucessfully as student to the course");
			}
			
			
		}
	}
	
	
	@Override
	public boolean enrollTAForCourse(User Ta, Course course) {
		// TODO Auto-generated method stub
		Role role= new Role("TA");
		boolean response=false;
		try {
			response= courseEnrollDB.enrollUserForCourse(Ta, course, role);
			
		} catch (UserDefinedSQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return response;
	}



	@Override
	public List<User> getTAForCourse(Course course) throws UserDefinedSQLException,CourseException {
		// TODO Auto-generated method stub
		List<User> listOfTA = new ArrayList<User>();
		Course returnedcourse = courseDB.getCourse(course.getCourseID());	
		if(returnedcourse!=null)
		{
			listOfTA = courseEnrollDB.getTaForCourse(course);
		}
		else {
			throw new CourseException("No Course Exist with provided Id");
		}
		return listOfTA;
	}

}
