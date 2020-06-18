package com.dal.catmeclone;


import java.io.IOException;
import java.util.Properties;

import org.springframework.core.env.Environment;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.DBUtility.DatabaseConnectionImpl;
import com.dal.catmeclone.DBUtility.PropertiesConfigUtil;
import com.dal.catmeclone.UserProfile.ForgotPasswordDao;
import com.dal.catmeclone.UserProfile.ForgotPasswordDaoImpl;
import com.dal.catmeclone.UserProfile.ForgotPasswordService;
import com.dal.catmeclone.UserProfile.ForgotPasswordServiceImpl;
import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.UserProfile.UserDaoImpl;
import com.dal.catmeclone.UserProfile.UserService;
import com.dal.catmeclone.UserProfile.UserServiceImpl;
import com.dal.catmeclone.admin.AdminService;
import com.dal.catmeclone.admin.AdminServiceImpl;
import com.dal.catmeclone.admin.CourseInstructorAssignmentDao;
import com.dal.catmeclone.admin.CourseInstructorAssignmentDaoImpl;
import com.dal.catmeclone.admin.CourseManagementDao;
import com.dal.catmeclone.admin.CourseManagementDaoImpl;
import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;
import com.dal.catmeclone.authenticationandauthorization.Interface_AuthenticateUserDao;
import com.dal.catmeclone.authenticationandauthorization.SuccessHandler;
import com.dal.catmeclone.authenticationandauthorization.UserAuthentication;
import com.dal.catmeclone.course.CourseDaoImpl;
import com.dal.catmeclone.course.CourseEnrollmentDao;
import com.dal.catmeclone.course.CourseEnrollmentDaoImpl;
import com.dal.catmeclone.course.CourseEnrollmentService;
import com.dal.catmeclone.course.CourseEnrollmentServiceImpl;
import com.dal.catmeclone.course.CourseService;
import com.dal.catmeclone.course.CourseServiceImpl;
import com.dal.catmeclone.course.CoursesDao;
import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;
import com.dal.catmeclone.encrypt.BCryptPasswordEncryptionImpl;
import com.dal.catmeclone.notification.NotificationService;
import com.dal.catmeclone.notification.NotificationServiceImpl;
import com.dal.catmeclone.questionmanagement.QuestionManagementDao;
import com.dal.catmeclone.questionmanagement.QuestionManagementDaoImpl;
import com.dal.catmeclone.questionmanagement.QuestionManagementService;
import com.dal.catmeclone.questionmanagement.QuestionManagementServiceImpl;



/*
 * This is a singleton, we will learn about these when we learn design patterns.
 * 
 * The single responsibility of this singleton is to store concrete classes
 * selected by the system for use in the rest of the system. This will allow
 * a form of dependency injection in places where we cannot use normal
 * dependency injection (for example classes that override or extend existing
 * library classes in the framework).
 */
public class SystemConfig
{
	private static SystemConfig uniqueInstance = null;
	private Properties properties;
	
	private String resourceFilename ="application.properties";
	
	private AdminService adminService;
	private CourseInstructorAssignmentDao courseInstructorAssignmentDao;
	private CourseManagementDao courseManagementDao;
	private Interface_AuthenticateUserDao authenticateUserDao;
	private CourseService courseService;
	private CourseEnrollmentService courseEnrollmentService;
	private CoursesDao  courseDao;
	private CourseEnrollmentDao courseEnrollmentDao;
	private DataBaseConnection databaseConnection;
	private BCryptPasswordEncryption bcryptPasswordEncrption;
	private NotificationService notificationService;
	private UserDao userDao;
	private UserService userService;
	private ForgotPasswordService forgotPasswordService;
	private ForgotPasswordDao forgotPasswordDao;
	private UserAuthentication userAuthentication;
	private QuestionManagementDao questionManagementDao;
	private QuestionManagementService questionManagementService;
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	private Environment env;
	private PropertiesConfigUtil propertiesConfig;
	
	
	
	public SystemConfig() {
		super();
		this.adminService = new AdminServiceImpl();
		this.courseInstructorAssignmentDao = new CourseInstructorAssignmentDaoImpl();
		this.courseManagementDao = new CourseManagementDaoImpl();
		this.authenticateUserDao = new AuthenticateUserDao();
		this.courseService = new CourseServiceImpl();
		this.courseEnrollmentService = new CourseEnrollmentServiceImpl();
		this.courseDao = new CourseDaoImpl();
		this.courseEnrollmentDao = new CourseEnrollmentDaoImpl();
		this.propertiesConfig = new PropertiesConfigUtil();
		this.properties = initializProperties(propertiesConfig);
		this.databaseConnection = new DatabaseConnectionImpl();
		this.bcryptPasswordEncrption = new BCryptPasswordEncryptionImpl();
		this.notificationService = new NotificationServiceImpl();
		this.userDao = new UserDaoImpl();
		this.userService = new UserServiceImpl();
		this.forgotPasswordService = new ForgotPasswordServiceImpl();
		this.forgotPasswordDao = new ForgotPasswordDaoImpl();
		this.userAuthentication = new UserAuthentication();
		this.authenticationSuccessHandler = new SuccessHandler();
		this.questionManagementService = new QuestionManagementServiceImpl();
		this.questionManagementDao = new QuestionManagementDaoImpl();
		
	}

	

	// This is the way the rest of the application gets access to the System object.
	public static SystemConfig instance()
	{
		// Using lazy initialization, this is the one and only place that the System
		// object will be instantiated.
		if (null == uniqueInstance)
		{
			uniqueInstance = new SystemConfig();
		}
		return uniqueInstance;
	}
	
	
	public Properties initializProperties(PropertiesConfigUtil propertiesConfig)
	{
		Properties property = null;
		try {
			property= propertiesConfig.loadProperties(resourceFilename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return property;
	}



	public AdminService getAdminService() {
		return adminService;
	}



	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}



	public CourseInstructorAssignmentDao getCourseInstructorAssignmentDao() {
		return courseInstructorAssignmentDao;
	}



	public void setCourseInstructorAssignmentDao(CourseInstructorAssignmentDao courseInstructorAssignmentDao) {
		this.courseInstructorAssignmentDao = courseInstructorAssignmentDao;
	}



	public CourseManagementDao getCourseManagementDao() {
		return courseManagementDao;
	}



	public void setCourseManagementDao(CourseManagementDao courseManagementDao) {
		this.courseManagementDao = courseManagementDao;
	}



	public Interface_AuthenticateUserDao getAuthenticateUserDao() {
		return authenticateUserDao;
	}



	public void setAuthenticateUserDao(Interface_AuthenticateUserDao authenticateUserDao) {
		this.authenticateUserDao = authenticateUserDao;
	}



	public CourseService getCourseService() {
		return courseService;
	}



	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}



	public CourseEnrollmentService getCourseEnrollmentService() {
		return courseEnrollmentService;
	}



	public void setCourseEnrollmentService(CourseEnrollmentService courseEnrollmentService) {
		this.courseEnrollmentService = courseEnrollmentService;
	}



	public CoursesDao getCourseDao() {
		return courseDao;
	}



	public void setCourseDao(CoursesDao courseDao) {
		this.courseDao = courseDao;
	}



	public CourseEnrollmentDao getCourseEnrollmentDao() {
		System.out.println(courseEnrollmentDao);
		return courseEnrollmentDao;
	}



	public void setCourseEnrollmentDao(CourseEnrollmentDao courseEnrollmentDao) {
		this.courseEnrollmentDao = courseEnrollmentDao;
	}



	public DataBaseConnection getDatabaseConnection() {
		return databaseConnection;
	}



	public void setDatabaseConnection(DataBaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}



	public BCryptPasswordEncryption getBcryptPasswordEncrption() {
		return bcryptPasswordEncrption;
	}



	public void setBcryptPasswordEncrption(BCryptPasswordEncryption bcryptPasswordEncrption) {
		this.bcryptPasswordEncrption = bcryptPasswordEncrption;
	}



	public NotificationService getNotificationService() {
		return notificationService;
	}



	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}



	public UserDao getUserDao() {
		return userDao;
	}



	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}



	public UserService getUserService() {
		return userService;
	}



	public void setUserService(UserService userService) {
		this.userService = userService;
	}



	public ForgotPasswordService getForgotPasswordService() {
		return forgotPasswordService;
	}



	public void setForgotPasswordService(ForgotPasswordService forgotPasswordService) {
		this.forgotPasswordService = forgotPasswordService;
	}



	public ForgotPasswordDao getForgotPasswordDao() {
		return forgotPasswordDao;
	}



	public void setForgotPasswordDao(ForgotPasswordDao forgotPasswordDao) {
		this.forgotPasswordDao = forgotPasswordDao;
	}



	public UserAuthentication getUserAuthentication() {
		return userAuthentication;
	}



	public void setUserAuthentication(UserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}



	public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
		return authenticationSuccessHandler;
	}



	public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
		this.authenticationSuccessHandler = authenticationSuccessHandler;
	}



	public Environment getEnv() {
		return env;
	}



	public void setEnv(Environment env) {
		this.env = env;
	}



	public PropertiesConfigUtil getPropertiesConfig() {
		return propertiesConfig;
	}



	public void setPropertiesConfig(PropertiesConfigUtil propertiesConfig) {
		this.propertiesConfig = propertiesConfig;
	}



	public Properties getProperties() {
		return properties;
	}



	public void setProperties(Properties properties) {
		this.properties = properties;
	}



	public QuestionManagementDao getQuestionManagementDao() {
		return questionManagementDao;
	}



	public void setQuestionManagementDao(QuestionManagementDao questionManagementDao) {
		this.questionManagementDao = questionManagementDao;
	}



	public QuestionManagementService getQuestionManagementService() {
		return questionManagementService;
	}



	public void setQuestionManagementService(QuestionManagementService questionManagementService) {
		this.questionManagementService = questionManagementService;
	}

	
	
}
