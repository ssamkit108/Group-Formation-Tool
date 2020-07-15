package com.dal.catmeclone.adminTest;

import com.dal.catmeclone.admin.CourseInstructorAssignmentDao;
import com.dal.catmeclone.admin.CourseManagementDao;

public interface IAdminAbstractFactory {
    public CourseInstructorAssignmentDao createCourseInstructorAssignmentDao();

    public CourseManagementDao createCourseManagementDao();

}
