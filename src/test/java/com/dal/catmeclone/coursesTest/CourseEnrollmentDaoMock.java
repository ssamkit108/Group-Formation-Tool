package com.dal.catmeclone.coursesTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dal.catmeclone.course.CourseEnrollmentDao;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class CourseEnrollmentDaoMock implements CourseEnrollmentDao {

	List<Course> listOfCourse = new ArrayList<Course>();

	Map<Course, List<User>> courseUserMapping = new HashMap<Course, List<User>>();

	public CourseEnrollmentDaoMock() {

		this.listOfCourse = new ArrayList<Course>();
		listOfCourse.add(new Course(100, "CourseA"));
		listOfCourse.add(new Course(101, "CourseB"));

		User Student1 = new User("B00000001");
		Student1.setUserRoles(new Role("Student"));
		User Student2 = new User("B00000002");
		Student2.setUserRoles(new Role("Student"));
		User TA1 = new User("B00000003");
		TA1.setUserRoles(new Role("TA"));
		User TA2 = new User("B00000004");
		TA2.setUserRoles(new Role("TA"));
		User instructor1 = new User("B00000005");
		instructor1.setUserRoles(new Role("Instructor"));
		User instructor2 = new User("B00000006");
		instructor2.setUserRoles(new Role("Instructor"));

		List<User> userenrolledincourseA = new ArrayList<User>();
		userenrolledincourseA.add(Student1);
		userenrolledincourseA.add(TA1);
		userenrolledincourseA.add(instructor1);
		courseUserMapping.putIfAbsent(listOfCourse.get(0), userenrolledincourseA);

		List<User> userenrolledincourseB = new ArrayList<User>();
		userenrolledincourseB.add(TA2);
		userenrolledincourseB.add(instructor2);
		courseUserMapping.putIfAbsent(listOfCourse.get(1), userenrolledincourseB);

	}

	@Override
	public boolean enrollUserForCourse(User student, Course course, Role role) {

		student.setUserRoles(role);
		if (listOfCourse.contains(course)) {
			List<User> userenrolledincourse = courseUserMapping.get(course);
			userenrolledincourse.add(student);
			if (courseUserMapping.putIfAbsent(course, userenrolledincourse) != null) {
				return true;
			} else {
				return false;
			}

		} else
			return false;

	}

	@Override
	public boolean hasEnrolledInCourse(String bannerId, int courseId) {

		boolean response = false;
		for (Course c : listOfCourse) {
			if (c.getCourseID() == courseId) {
				List<User> userenrolledincourse = courseUserMapping.get(c);
				for (User u : userenrolledincourse) {
					if (u.getBannerId().equals(bannerId)) {
						response = true;
						break;
					}
				}
			}
		}
		return response;
	}

	@Override
	public List<Course> getAllEnrolledCourse(User user1) {

		List<Course> courselist = new ArrayList<Course>();

		for (Course c : listOfCourse) {
			List<User> userenrolledincourse = courseUserMapping.get(c);
			for (User u : userenrolledincourse) {
				if (u.getBannerId().equals(user1.getBannerId())) {
					courselist.add(c);
				}
			}
		}

		return listOfCourse;
	}

	@Override
	public Role getUserRoleForCourse(User user, Course course) {

		Role role = null;

		for (Course c : listOfCourse) {
			if (c.getCourseID() == course.getCourseID()) {
				List<User> userenrolledincourse = courseUserMapping.get(c);
				for (User u : userenrolledincourse) {
					if (u.getBannerId().equals(user.getBannerId())) {
						role = u.getUserRoles();
					}
				}
			}
		}

		return role;
	}

}
