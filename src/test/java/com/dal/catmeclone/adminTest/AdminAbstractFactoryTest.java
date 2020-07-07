package com.dal.catmeclone.adminTest;

import com.dal.catmeclone.admin.CourseInstructorAssignmentDao;
import com.dal.catmeclone.admin.CourseManagementDao;

public interface AdminAbstractFactoryTest {
	public CourseInstructorAssignmentDao createCourseInstructorAssignmentDao();
	public CourseManagementDao createCourseManagementDao();
    
}
