package com.dal.catmeclone.coursesTest;

import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.course.CourseAbstractFactory;
import com.dal.catmeclone.course.CourseEnrollmentDao;
import com.dal.catmeclone.course.CourseEnrollmentService;
import com.dal.catmeclone.course.CourseEnrollmentServiceImpl;
import com.dal.catmeclone.course.CourseService;
import com.dal.catmeclone.course.CourseServiceImpl;
import com.dal.catmeclone.course.CoursesDao;

public class CourseAbstractFactoryTestImpl implements CourseAbstractFactory {

    @Override
    public CourseEnrollmentDao createCourseEnrollmentDao() {
       
        return new CourseEnrollmentDaoMock();
    }

	@Override
	public CoursesDao createCourseDao() {
		 return new CourseDaoMock();
	}

	@Override
	public CourseService createCourseService() {
		
		return new CourseServiceImpl();
	}

	@Override
	public CourseEnrollmentService createCourseEnrollmentService() {
	
		return new CourseEnrollmentServiceImpl();
	}

	@Override
	public CourseService createCourseService(CoursesDao courseDao) {
		return new CourseServiceImpl(courseDao);
	}

	@Override
	public CourseEnrollmentService createCourseEnrollmentService(UserDao userDB, CourseEnrollmentDao courseEnroll) {
		return new CourseEnrollmentServiceImpl(userDB, courseEnroll);
	}

}
