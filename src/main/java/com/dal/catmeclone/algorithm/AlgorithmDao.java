package com.dal.catmeclone.algorithm;

import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

public interface AlgorithmDao {
    List<String> getAllStudents(int courseid) throws UserDefinedSQLException;
    
    List<String> getAllResponsesOfAStudent(String bannerid, int courseid) throws UserDefinedSQLException;

    List<String> getSurveyCriteria(int courseid) throws UserDefinedSQLException;

    int getGroupSizeForCourse(int courseid) throws UserDefinedSQLException;

}
