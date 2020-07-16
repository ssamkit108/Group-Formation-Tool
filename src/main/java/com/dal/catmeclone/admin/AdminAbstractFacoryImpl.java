package com.dal.catmeclone.admin;

import com.dal.catmeclone.UserProfile.UserDao;

public class AdminAbstractFacoryImpl implements AdminAbstractFactory {
    @Override
    public AdminService createAdminService() {
        return new AdminServiceImpl();
    }

    @Override
    public CourseInstructorAssignmentDao createCourseInstructorAssignmentDao() {
        return new CourseInstructorAssignmentDaoImpl();
    }

    @Override
    public CourseManagementDao createCourseManagementDao() {
        return new CourseManagementDaoImpl();
    }

	@Override
	public AdminService AdminServiceImpl(CourseInstructorAssignmentDao courseInstructor,
			CourseManagementDao courseManagement, UserDao userDao) {
		return new AdminServiceImpl(courseInstructor, courseManagement, userDao);
	}
}
