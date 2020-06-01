package com.dal.catmeclone.CreateUserTest;

import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;

public class BCryptPasswordEncryptionMock implements BCryptPasswordEncryption {

	@Override
	public String encryptPassword(String rawPassword) {
		return "Encrypted";
	}

	@Override
	public boolean matches(String rawPassword, String encryptedPassword) {
		if(encryptedPassword == null || encryptedPassword.isEmpty() || rawPassword==null || rawPassword.isEmpty()) {
			return false;
		}
		return rawPassword.equals(encryptedPassword);
	}

}
