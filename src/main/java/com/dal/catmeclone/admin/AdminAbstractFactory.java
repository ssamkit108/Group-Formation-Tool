package com.dal.catmeclone.admin;

import com.dal.catmeclone.UserProfile.UserDao;

public interface AdminAbstractFactory {
    public AdminService createAdminService();
    public AdminService AdminServiceImpl(CourseInstructorAssignmentDao courseInstructor, CourseManagementDao courseManagement,
			UserDao userDao);
    public CourseInstructorAssignmentDao createCourseInstructorAssignmentDao();
    public CourseManagementDao createCourseManagementDao();
}
