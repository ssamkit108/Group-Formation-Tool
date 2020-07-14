package com.dal.catmeclone.algorithm;

import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

public interface AlgorithmService {

    public List<List<String>> groupFormation(Course course) throws UserDefinedSQLException;

}
