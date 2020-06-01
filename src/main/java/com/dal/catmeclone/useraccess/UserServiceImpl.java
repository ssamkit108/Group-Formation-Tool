package com.dal.catmeclone.useraccess;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDB;
	
	@Override
	public List<User> findAllMatchingUser(String bannerId) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		List<User> listOfUser= new ArrayList<User>();
		listOfUser=userDB.findAllMatchingUser(bannerId);
		return listOfUser;
	}

}
