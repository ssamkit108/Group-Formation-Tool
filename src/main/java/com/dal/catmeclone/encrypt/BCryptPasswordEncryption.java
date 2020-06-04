package com.dal.catmeclone.encrypt;

public interface BCryptPasswordEncryption {
	
	public String encryptPassword(String rawPassword);
	
	public boolean matches(String rawPassword, String encryptedPassword);

}
