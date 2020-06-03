package com.dal.catmeclone.authenticationandauthorization;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncryption implements Interface_PasswordEncryption {
	
	private BCryptPasswordEncoder encoder;

	public BCryptPasswordEncryption() 
	{
		encoder=new BCryptPasswordEncoder();
	}
	
	public String encryptPassword(String rawPassword)
	{
		return encoder.encode(rawPassword);
	}
	
	public boolean matches(String rawPassword, String encryptedPassword)
	{
		return encoder.matches(rawPassword, encryptedPassword);
	}
}
