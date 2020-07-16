package com.dal.catmeclone.course;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.UserProfile.UserProfileAbstractFactory;
import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;
import com.dal.catmeclone.encrypt.EncryptAbstractFactory;
import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;
import com.dal.catmeclone.exceptionhandler.FileRelatedException;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.notification.NotificationAbstractFactory;
import com.dal.catmeclone.notification.NotificationService;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

	private static final Logger LOGGER = Logger.getLogger(CourseEnrollmentServiceImpl.class.getName());

	AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	CourseAbstractFactory courseAbstractFactory = abstractFactory.createCourseAbstractFactory();
	NotificationAbstractFactory notificationAbstractFactory = abstractFactory.createNotificationAbstractFactory();
	UserProfileAbstractFactory userProfileAbstractFactory = abstractFactory.createUserProfileAbstractFactory();
	EncryptAbstractFactory encryptAbstractFactory = abstractFactory.createEncryptAbstractFactory();

	private static final String email_regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	public List<String> recordsSuccessMessage = new ArrayList<String>();
	public List<String> recordsFailureMessage = new ArrayList<String>();
	String ALPHA_NUMERIC_STRING;

	NotificationService notificationService;
	UserDao userDB;
	CourseEnrollmentDao courseEnrollDB;
	BCryptPasswordEncryption bcryptEncoder;
	
	public CourseEnrollmentServiceImpl() {
		super();
		userDB = userProfileAbstractFactory.createUserDao();
		courseEnrollDB = courseAbstractFactory.createCourseEnrollmentDao();
	}

	
	public CourseEnrollmentServiceImpl(UserDao userDB, CourseEnrollmentDao courseEnrollDB) {
		super();
		this.userDB = userDB;
		this.courseEnrollDB = courseEnrollDB;
	}


	public List<String> getRecordsSuccessMessage() {
		return recordsSuccessMessage;
	}

	public List<String> getRecordsFailureMessage() {
		return recordsFailureMessage;
	}

	@Override
	public boolean enrollStudentForCourse(MultipartFile file, Course course)
			throws FileRelatedException, UserDefinedException {
		recordsSuccessMessage = new ArrayList<String>();
		recordsFailureMessage = new ArrayList<String>();
		Set<User> usersToBeEnrolled = new HashSet<User>();
		LOGGER.info("Processing the Provided CSV File: " + file.getName());
		usersToBeEnrolled = loadDataFromCSV(file);

		Iterator<User> listIterator = usersToBeEnrolled.iterator();
		LOGGER.info("Saving/updating the entities details in database");
		while (listIterator.hasNext()) {
			User student = listIterator.next();
			// call enrollStudent method to enroll student
			enrollStudent(student, course);
		}
		return true;
	}

	private Set<User> loadDataFromCSV(MultipartFile file) throws FileRelatedException {
		Set<User> usersToBeEnrolled = new HashSet<User>();
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String line = bufferReader.readLine();
			int count = 1;
			// reading the file line by line
			LOGGER.info("START: Parsing the Provided CSV File: " + file.getName());
			while ((line = bufferReader.readLine()) != null) {
				// considering comma as separator for csv
				String[] details = line.split(",");
				if (4 == details.length) {
					// validating if banner id field is null or empty
					if (null != details[0] && !details[0].isEmpty()) {
						// validating if email field is null or empty
						if (null != details[3] && !details[3].isEmpty()) {
							Pattern pattern = Pattern.compile(email_regex);
							Matcher matcher = pattern.matcher(details[3]);
							// validating email pattern
							if (matcher.matches()) {
								User student = new User(details[0], details[1], details[2], details[3]);
								usersToBeEnrolled.add(student);
							} else {
								LOGGER.warning("Email provided at row " + (count) + " is not valid email");
								recordsFailureMessage.add("Email provided at row " + (count) + " is not valid email");
							}
						} else {
							LOGGER.warning("Email provided at row " + (count) + "is not valid");
							recordsFailureMessage.add("Email provided at row " + (count) + "is not valid");
						}
					} else {
						LOGGER.warning("Banner ID provided at row " + (count) + " is not valid");
						recordsFailureMessage.add("Banner ID provided at row " + (count) + " is not valid");
					}
				} else {
					LOGGER.warning("Details provided at row " + (count) + " are not valid");
					recordsFailureMessage.add("Details provided at row " + (count) + " is	 not valid");
				}
				count++;
			}
			LOGGER.info("END: Parsing the Provided CSV File");
		} catch (FileNotFoundException e) {
			LOGGER.warning("Error occured while processing file");
			throw new FileRelatedException("Error Occured in reading file. Please verify and try again");
		} catch (IOException e) {
			LOGGER.warning("Error occured while reading file");
			throw new FileRelatedException("Error Occured in reading file. Please verify and try again");
		}
		return usersToBeEnrolled;
	}

	private void enrollStudent(User user, Course course) throws UserDefinedException {

		notificationService = notificationAbstractFactory.createNotificationService();
		bcryptEncoder = encryptAbstractFactory.createBCryptPasswordEncryption();

		// Check if user exists with the given banner id or not
		User userFetched = userDB.findUserByBannerID(user.getBannerId());

		if (null != userFetched) {
			// Checking is user has already enrolled in the course
			if (!courseEnrollDB.hasEnrolledInCourse(user.getBannerId(), course.getCourseID())) {
				Role role = new Role("Student");
				courseEnrollDB.enrollUserForCourse(user, course, role);
				LOGGER.info(
						"User with BannerId: " + user.getBannerId() + " enroll sucessfully as student to the course");
				recordsSuccessMessage.add(
						"User with BannerId: " + user.getBannerId() + " enroll sucessfully as student to the course");
			} else {
				LOGGER.info("User with " + user.getBannerId() + " already enrolled in the course");
				recordsFailureMessage.add("User with " + user.getBannerId() + " already enrolled in the course");
			}
		}
		// If user is not existing. create a profile for user and enroll user in course
		else {
			String password = GeneratePassword();
			String passwordToBeSend = new String(password);
			user.setPassword(bcryptEncoder.encryptPassword(password));
			bcryptEncoder.encryptPassword(password);
			boolean isCreated = false;
			try {
				// Create User in System
				isCreated = userDB.createUser(user);
			} catch (DuplicateEntityException e) {
				// Handle error for error thrown is another exist with same email id
				recordsFailureMessage.add("User already exists with " + user.getEmail());
			}
			if (isCreated) {
				LOGGER.info("User created successfully");
				Role role = new Role("Student");
				courseEnrollDB.enrollUserForCourse(user, course, role);
				LOGGER.info(
						"User with BannerId: " + user.getBannerId() + " enroll sucessfully as student to the course");
				notificationService.sendNotificationToNewuser(user, passwordToBeSend, course);
				LOGGER.info("Notification email send to user: " + user.getBannerId());
				recordsSuccessMessage.add("User with BannerId: " + user.getBannerId()
						+ " created and enroll sucessfully as student to the course");
			}
		}
	}

	@Override
	public boolean enrollTAForCourse(User Ta, Course course) {
		Role role = new Role("TA");
		boolean response = false;
		try {
			LOGGER.info("Calling database to enroll TA for the given course");
			response = courseEnrollDB.enrollUserForCourse(Ta, course, role);
		} catch (UserDefinedException e) {
			return false;
		}
		return response;
	}

	@Override
	public List<Course> getCourseEnrolledForUser(User user) throws UserDefinedException {
		// calling Database access layer to get the list of user enrolled in course
		List<Course> listofCourses = new ArrayList<Course>();
		LOGGER.info("calling Database access layer to get the list of course enrolled by user");
		listofCourses = courseEnrollDB.getAllEnrolledCourse(user);
		return listofCourses;
	}

	@Override
	public Role getUserRoleForCourse(User user, Course course) throws UserDefinedException {
		Role role = null;
		LOGGER.info("Calling database to get the role for the user");
		role = courseEnrollDB.getUserRoleForCourse(user, course);
		return role;
	}

	public String GeneratePassword() {

		Properties properties = SystemConfig.instance().getProperties();
		ALPHA_NUMERIC_STRING = properties.getProperty("random");
		StringBuilder builder = new StringBuilder();
		builder.setLength(0);
		LOGGER.info("Generating Random Alphanumeric Passwor");
		for (int i = 0; i < 8; i++) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		String newPassword = builder.toString();
		return newPassword;
	}

}
