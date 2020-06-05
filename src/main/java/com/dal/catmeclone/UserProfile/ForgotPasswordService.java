package com.dal.catmeclone.UserProfile;


public interface ForgotPasswordService {
	
	public boolean forgotpassword(String username) throws Exception;
	
	public boolean ValidateUser(String username) throws Exception;

	public String GeneratePassword();

}
