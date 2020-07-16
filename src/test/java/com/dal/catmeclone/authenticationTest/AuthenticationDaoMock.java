package com.dal.catmeclone.authenticationTest;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class AuthenticationDaoMock implements AuthenticateUserDao {

	AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelFactory = abstractFactoryTest.createModelAbstractFactory();
    User user;

    public AuthenticationDaoMock() {
    	user = modelFactory.createUser("B00832190"); 
    	Role role = modelFactory.createRole();
    	role.setRoleName("Guest");
    	user.setPassword(new BCryptPasswordEncoder().encode("Password"));
    	user.setUserRoles(role);
    }

	@Override
	public User authenticateUser(User user) {
		if (this.user.getBannerId().equals(user.getBannerId())) {
			return this.user;
		} else {
			return null;
		}
	}

}
