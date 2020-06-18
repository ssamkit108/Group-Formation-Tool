package com.dal.catmeclone.authenticationTest;


import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;

import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class AuthenticationDaoMock implements AuthenticateUserDao {

	private String bannerId;
	private String password;
	private int role_id;
	private String role_tagged;

	public AuthenticationDaoMock() {
		default_data();
	}

	public void default_data() {
		bannerId = "B00832190";
		password = "password";
		role_id = 1;
		role_tagged = "Student";

	}

	@Override
	public User authenticateUser(User user) {
		user.setBannerId(bannerId);
		user.setPassword(password);
		Role rl = new Role();
		rl.setRoleId(role_id);
		rl.setRoleName(role_tagged);
		user.setUserRoles(rl);
		return user;
	}

}
