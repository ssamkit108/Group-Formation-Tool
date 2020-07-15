package com.dal.catmeclone.admin;

public interface AdminAbstractFactory {
    public AdminService createAdminService();

    public CourseInstructorAssignmentDao createCourseInstructorAssignmentDao();

    public CourseManagementDao createCourseManagementDao();
}
