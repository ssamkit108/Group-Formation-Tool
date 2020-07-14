package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmServiceImpl implements AlgorithmService {

	AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	AlgorithmAbstractFactory algorithmAbstractFactory = abstractFactory.createAlgorithmAbstractFactory();
	AlgorithmDao algorithmDao = algorithmAbstractFactory.createAlgorithmDao();

	@Override
	public List<List<String>> groupFormation(Course course) throws UserDefinedSQLException {

		//  Create student list
		List<String> studentsList = new ArrayList<>();
		studentsList = algorithmDao.getAllStudents(course.getCourseID());
		System.out.println(studentsList);

		List<List<String>> responses = new ArrayList<>();

		List<String> currentGroup;

		List<List<String>> groupsFormed = new ArrayList<>();

		for (int i = 0; i < studentsList.size(); i++) {
			responses.add(algorithmDao.getAllResponsesOfAStudent(studentsList.get(i), course.getCourseID()));
		}

		System.out.println(responses);

		List<String> criteria = new ArrayList<>();
		criteria = algorithmDao.getSurveyCriteria(course.getCourseID());

		System.out.println(criteria);

		int groupSize = algorithmDao.getGroupSizeForCourse(course.getCourseID());

		System.out.println(groupSize);

		int criteriaMatches;

		int noofquestions = criteria.size();
		int noofstudents = studentsList.size();

		// Group size is always greater than 1
		int[][] distMatrix = new int[noofstudents + 1][noofstudents + 1];

		// Calculate number of clusters
		int noofclusters = noofstudents / groupSize;
		if ((noofstudents % groupSize) != 0) {
			noofclusters++;
		}

		// Create student set
		List<Integer> students = new ArrayList<>();

		// Create already assigned set
		List<Integer> alreadyGroupedStudents = new ArrayList<>();

		// Create list of students
		for (int i = 1; i <= noofstudents; i++) {
			students.add(i);
		}

		int counter = noofclusters;
		int currentgroup = 1;
		int maxVertex = -1;
		int selectedVertex = -1;
		int maxI = 1, maxJ = 2;
		int maxValue = -1;

		// Maintain list of specialStudents
		List<Integer> specialStudentsGroup = new ArrayList<>();

		for (int i = 1; i <= noofstudents; i++) {
			for (int j = 1; j <= noofstudents; j++) {
				criteriaMatches = 0;
				// Similar Case
				if (i == j) {
					distMatrix[i][j] = 0;
				} else {
					for (int k = 1; k <= noofquestions; k++) {
						if (criteria.get(k - 1).equals("Group Similar")) {
							if (responses.get(i - 1).get(k - 1).equals(responses.get(j - 1).get(k - 1))) {
								criteriaMatches = criteriaMatches + 1;
							}
						}

						// Not Similar Case
						else if (criteria.get(k - 1).equals("Not Similar")) {

							if (responses.get(i - 1).get(k - 1).equals(responses.get(j - 1).get(k - 1)) == false) {
								criteriaMatches = criteriaMatches + 1;
							}

						}
					}
					distMatrix[i][j] = criteriaMatches;
				}
				System.out.print(distMatrix[i][j]);
				System.out.print("\t");
			}
			System.out.println("\n");
		}

		int globalMaxAverageDistance, localMaxAverageDistance;

		while (counter != 0) {

			// If special students is not empty first insert them into a group
			if (specialStudentsGroup.isEmpty() == false && (alreadyGroupedStudents.isEmpty())) {
				// Each time get the first index and insert them in the group and remove from
				// special students
				System.out.println("Inserting special student" + specialStudentsGroup.get(0) + "\n");
				alreadyGroupedStudents.add(specialStudentsGroup.get(0));
				students.remove(new Integer(specialStudentsGroup.get(0)));
				specialStudentsGroup.remove(0);

//        					currentgroup = currentgroup + 1;

			} else {

				// Get next groups students
				if (alreadyGroupedStudents.isEmpty() && students.isEmpty() == false) {
					// Get minI, minJ and assign it to next group as a starting point
					// Can be written in a function

					// Case where only one student is left to be grouped
					if (students.size() == 1) {
						alreadyGroupedStudents.add(students.get(0));
						students.remove(0);
					} else {
						maxValue = -1;
						for (int i = 0; i < students.size(); i++) {
							for (int j = i+1; j < students.size(); j++) {
								if (distMatrix[students.get(i)][students.get(j)] > maxValue) {
									maxValue = distMatrix[students.get(i)][students.get(j)];
									maxI = students.get(i);
									maxJ = students.get(j);
								}
							}
						}
						// Add vertices to alreadyGroupedStudents
						alreadyGroupedStudents.add(maxI);
						alreadyGroupedStudents.add(maxJ);
						students.remove(new Integer(maxI));
						students.remove(new Integer(maxJ));
					}
				}

				else {

					// Implement this isGroupFilled (Check here if sum of group1 row is equal to
					// group size alreadyGroupedStudents.size() == groupsize || students.isEmpty()
					if (alreadyGroupedStudents.size() == groupSize || students.isEmpty()) {
						currentGroup = new ArrayList<String>();
						System.out.println((currentgroup - 1) + ":");
						System.out.println(alreadyGroupedStudents);
						for (int i = 1; i <= alreadyGroupedStudents.size(); i++) {
							currentGroup.add(studentsList.get(alreadyGroupedStudents.get(i - 1) - 1));
						}
						groupsFormed.add(currentGroup);
						currentGroup = null;
						alreadyGroupedStudents.clear();

						// Increase current group count to go to next group
						currentgroup = currentgroup + 1;

						// Decrease cluster count
						counter = counter - 1;
					} else {
						if( students.size() == specialStudentsGroup.size()) {
							alreadyGroupedStudents.add(students.get(0));
							specialStudentsGroup.remove(new Integer(students.get(0)));
							students.remove(0);
						}
						// To add one student in the group size of current group should be less than
						// group-size given by instructor
						else {
						globalMaxAverageDistance = -1;
						// Check next minimum distance from both already selected i, j and calculate min
						// of both
						localMaxAverageDistance = -1;
						for (int i = 0; i < students.size(); i++) {
							if(specialStudentsGroup.contains(students.get(i)) == false) {
							if (localMaxAverageDistance > globalMaxAverageDistance) {
								globalMaxAverageDistance = localMaxAverageDistance;
								selectedVertex = maxVertex;
							}
							localMaxAverageDistance = 0;
							for (int j = 0; j < alreadyGroupedStudents.size(); j++) {

								// Calculate average
								localMaxAverageDistance = localMaxAverageDistance
										+ distMatrix[alreadyGroupedStudents.get(j)][students.get(i)];
							}
							localMaxAverageDistance = localMaxAverageDistance / 2;
							maxVertex = students.get(i);
						}
					}

						// Exception case handling if local Max is greater than global max for inserting
						// last student
						if (localMaxAverageDistance > globalMaxAverageDistance) {
							globalMaxAverageDistance = localMaxAverageDistance;
							selectedVertex = maxVertex;
						}

						alreadyGroupedStudents.add(selectedVertex);
						students.remove(new Integer(selectedVertex));

					}
				  }
				}
			}
		}
		return groupsFormed;

	}
}
