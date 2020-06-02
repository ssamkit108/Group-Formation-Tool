package com.dal.catmeclone.UserProfile;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDb;

	Boolean flag=false;
	
	final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

	@Override
	public boolean Create(User u) {
		try {
			if (User.isBannerIDValid(u.getBannerId()) &&
					 User.isEmailValid(u.getEmail()) &&
					 User.isFirstNameValid(u.getFirstName()) &&
					 User.isLastNameValid(u.getLastName()) &&
					 !u.getPassword().isEmpty())
			{
				flag=userDb.createUser(u);
			}
			return flag;
		} catch (Exception e) {
			flag=false;
			logger.error(e.getMessage());
			return flag;
		}
		}
	}

