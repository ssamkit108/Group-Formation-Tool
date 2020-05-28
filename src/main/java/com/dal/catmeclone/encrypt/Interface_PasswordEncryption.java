package com.dal.catmeclone.encrypt;

public interface Interface_PasswordEncryption {
	
	public String encryptPassword(String rawPassword);
	
	public boolean matches(String rawPassword, String encryptedPassword);

}
