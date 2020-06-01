package com.dal.catmeclone.CreateUserTest;

import com.dal.catmeclone.model.User;

public class UserValidateMock {
	
	public boolean validate(User u) {
		if(u.getBannerId().isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
}
