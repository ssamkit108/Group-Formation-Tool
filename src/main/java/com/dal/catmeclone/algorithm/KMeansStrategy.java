package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.model.SurveyQuestionResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KMeansStrategy implements GroupFormationStrategy {
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    AlgorithmAbstractFactory algorithmAbstractFactory = abstractFactory.createAlgorithmAbstractFactory();
    AlgorithmDao algorithmDao = algorithmAbstractFactory.createAlgorithmDao();

    int groupSize = 0;
    List<List<String>> groups = new ArrayList<>();
    List<String> studentsList = new ArrayList<>();
    List<String> setOfAllFreeTextResponse = new ArrayList<String>();
    List<SurveyQuestion> surveyQuestions = new ArrayList<SurveyQuestion>();
    Map<Integer, Integer> multipleChoiceMaxOptionIndex = new HashMap<Integer, Integer>();

    public KMeansStrategy() {
        super();
    }

    public KMeansStrategy(AlgorithmDao algorithmDao) {
        super();
        this.algorithmDao = algorithmDao;
    }

    @Override
    public Boolean formatGroupForCourse(Course course) throws UserDefinedException, Exception {
        List<List<SurveyQuestionResponse>> userSurveyResponse = new ArrayList<List<SurveyQuestionResponse>>();
        List<List<Integer>> formattedResponses = new ArrayList<>();
        List<String> criteria = new ArrayList<String>();
        boolean result = false;

        try {
            fetchAllDetailsRegardingSurvey(userSurveyResponse, course);
        } catch (UserDefinedException e) {
            throw new UserDefinedException("SQL Exception generated" + e.getLocalizedMessage());
        }

        formatAllResponsesOfAllStudents(userSurveyResponse, formattedResponses);

        criteria = getCriteriaList(surveyQuestions);

        groups = formGroup(formattedResponses, criteria, studentsList, groupSize);
        result = algorithmDao.updateGroupsFormed(groups, course.getCourseID());

        return result;
    }

    public List<List<String>> formGroup(List<List<Integer>> formattedResponses, List<String> criteria,
                                        List<String> studentsList, int groupSize) {
        List<List<String>> groupsFormed = new ArrayList<>();
        List<String> currentGroup;

        // Create student set
        List<Integer> students = new ArrayList<>();

        // Create already assigned set
        List<Integer> alreadyGroupedStudents = new ArrayList<>();

        // Maintain list of specialStudents
        List<Integer> specialStudentsGroup = new ArrayList<>();

        int criteriaMatches;
        int noofquestions = criteria.size();
        int noofstudents = studentsList.size();
        int currentgroup = 1;
        int maxVertex = -1;
        int selectedVertex = -1;
        int maxI = 1, maxJ = 2;
        int maxValue = -1;

        // Group size is always greater than 1
        int[][] distMatrix = new int[noofstudents + 1][noofstudents + 1];

        // Calculate number of clusters
        int noofclusters = 1;
        if (groupSize != 0) {
            noofclusters = 0;
            noofclusters = noofstudents / groupSize;

            if ((noofstudents % groupSize) != 0) {

                noofclusters++;
            }
        }
        int counter = noofclusters;

        // Create list of students
        for (int i = 1; i <= noofstudents; i++) {
            students.add(i);
        }

        for (int i = 1; i <= noofstudents; i++) {
            for (int j = 1; j <= noofstudents; j++) {
                criteriaMatches = 0;
                // Similar Case
                if (i == j) {
                    distMatrix[i][j] = 0;
                } else {
                    for (int k = 1; k <= noofquestions; k++) {
                        if (criteria.get(k - 1).equals("Group Similiar")) {
                            if (formattedResponses.get(i - 1).get(k - 1) == formattedResponses.get(j - 1).get(k - 1)) {
                                criteriaMatches = criteriaMatches + 1;
                            }
                        }
                        // Not Similar Case
                        else if (criteria.get(k - 1).equals("Group Disimilar")) {

                            if (formattedResponses.get(i - 1).get(k - 1) != formattedResponses.get(j - 1).get(k - 1)) {
                                criteriaMatches = criteriaMatches + 1;
                            }
                        }
                    }
                    distMatrix[i][j] = criteriaMatches;
                }

            }

        }
        int globalMaxAverageDistance, localMaxAverageDistance;

        while (counter != 0) {
            // If special students is not empty first insert them into a group
            if (specialStudentsGroup.isEmpty() == false && (alreadyGroupedStudents.isEmpty())) {
                // Each time get the first index and insert them in the group and remove from
                // special students

                alreadyGroupedStudents.add(specialStudentsGroup.get(0));
                students.remove(new Integer(specialStudentsGroup.get(0)));
                specialStudentsGroup.remove(0);
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
                            for (int j = i + 1; j < students.size(); j++) {
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
                } else {
                    // Implement this isGroupFilled (Check here if sum of group1 row is equal to
                    // group size alreadyGroupedStudents.size() == groupsize || students.isEmpty()
                    if (alreadyGroupedStudents.size() == groupSize || students.isEmpty()) {
                        currentGroup = new ArrayList<String>();

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
                        if (students.size() == specialStudentsGroup.size()) {
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
                                if (specialStudentsGroup.contains(students.get(i)) == false) {
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

    public void processMultipleChoiceAndFreeTextResponse(List<SurveyQuestionResponse> response) {
        int j = 0;
        for (SurveyQuestionResponse surveyQuestionResponse : response) {
            if (surveyQuestionResponse.getSurveyQuestion().getQuestionDetail()
                    .getQuestionType() == QuestionType.MULTIPLECHOICEMANY) {
                if (!multipleChoiceMaxOptionIndex.isEmpty() && multipleChoiceMaxOptionIndex.get(j) != null) {
                    if (surveyQuestionResponse.getResponse().size() > multipleChoiceMaxOptionIndex.get(j)) {
                        multipleChoiceMaxOptionIndex.put(j, surveyQuestionResponse.getResponse().size());
                    }
                } else {
                    multipleChoiceMaxOptionIndex.put(j, surveyQuestionResponse.getResponse().size());
                }

            }
            if (surveyQuestionResponse.getSurveyQuestion().getQuestionDetail()
                    .getQuestionType() == QuestionType.FREETEXT) {
                String textResponse = surveyQuestionResponse.getResponse().get(0).toString().toLowerCase();
                if (!setOfAllFreeTextResponse.contains(textResponse)) {
                    setOfAllFreeTextResponse.add(textResponse);
                }
            }
            j++;
        }
    }

    public void fetchAllDetailsRegardingSurvey(List<List<SurveyQuestionResponse>> userSurveyResponse, Course course)
            throws UserDefinedException, Exception {
        surveyQuestions = algorithmDao.getSurveyQuestionsForCourse(course.getCourseID());
        groupSize = algorithmDao.getGroupSizeForCourse(course.getCourseID());
        studentsList = algorithmDao.getAllStudents(course.getCourseID());
        for (int i = 0; i < studentsList.size(); i++) {
            List<SurveyQuestionResponse> response = algorithmDao.getAllResponsesOfAStudent(studentsList.get(i),
                    surveyQuestions);
            processMultipleChoiceAndFreeTextResponse(response);
            userSurveyResponse.add(response);
        }
    }

    public List<String> getCriteriaList(List<SurveyQuestion> surveyQuestions) {
        int m = 0;
        List<String> criteria = new ArrayList<String>();
        for (SurveyQuestion question : surveyQuestions) {
            if (question.getQuestionDetail().getQuestionType() == QuestionType.MULTIPLECHOICEMANY) {
                int entryToBeMade = multipleChoiceMaxOptionIndex.get(m);
                int i = 1;
                while (i <= entryToBeMade) {
                    criteria.add(question.getAlgorithmLogicType());
                    i++;
                }
            } else {
                criteria.add(question.getAlgorithmLogicType());
            }
            m++;
        }
        return criteria;
    }

    public List<List<Integer>> formatAllResponsesOfAllStudents(List<List<SurveyQuestionResponse>> userSurveyResponse,
                                                               List<List<Integer>> formattedResponses) {
        for (List<SurveyQuestionResponse> eachUserResponse : userSurveyResponse) {
            int j = 0;
            ArrayList<Integer> groupedResponse = new ArrayList<Integer>();
            for (SurveyQuestionResponse eachResponse : eachUserResponse) {
                // System.out.println(eachResponse.getResponse().toString());

                if (eachResponse.getSurveyQuestion().getQuestionDetail()
                        .getQuestionType() == QuestionType.MULTIPLECHOICEMANY) {
                    int maxResponseProvided = multipleChoiceMaxOptionIndex.get(j);
                    if (eachResponse.getResponse().size() < maxResponseProvided) {
                        for (int k = eachResponse.getResponse().size(); k < maxResponseProvided; k++) {
                            eachResponse.getResponse().add(0);
                        }
                    }
                }
                if (eachResponse.getSurveyQuestion().getQuestionDetail().getQuestionType() == QuestionType.NUMERIC) {

                    String algorithmType = eachResponse.getSurveyQuestion().getAlgorithmLogicType();
                    int constraintValue = eachResponse.getSurveyQuestion().getLogicConstraintValue();
                    if (algorithmType.equalsIgnoreCase("Greater Than")) {
                        if ((int) eachResponse.getResponse().get(0) > constraintValue) {
                            eachResponse.getResponse().set(0, 1);
                        } else {
                            eachResponse.getResponse().set(0, 0);
                        }
                    } else if (algorithmType.equalsIgnoreCase("Less Than")) {
                        if ((int) eachResponse.getResponse().get(0) < constraintValue) {
                            eachResponse.getResponse().set(0, 1);
                        } else {
                            eachResponse.getResponse().set(0, 0);
                        }
                    }
                }
                if (eachResponse.getSurveyQuestion().getQuestionDetail().getQuestionType() == QuestionType.FREETEXT) {
                    int index = setOfAllFreeTextResponse
                            .indexOf(eachResponse.getResponse().get(0).toString().toLowerCase());
                    eachResponse.getResponse().set(0, index);
                }
                // System.out.println(eachResponse.getResponse().toString());
                for (Object object : eachResponse.getResponse()) {
                    groupedResponse.add((Integer) (object));
                }
                // System.out.println(groupedResponse);
                j++;
            }
            formattedResponses.add(groupedResponse);
        }
        return formattedResponses;
    }

}
