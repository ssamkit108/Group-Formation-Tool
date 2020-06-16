package com.dal.catmeclone.encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

public class BCryptPasswordEncryptionImpl implements BCryptPasswordEncryption {
	
	private BCryptPasswordEncoder encoder;

	public BCryptPasswordEncryptionImpl() 
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
