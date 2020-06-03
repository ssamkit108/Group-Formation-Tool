package com.dal.catmeclone.UserProfile;


public interface ForgotPasswordService {
	
	public boolean forgotpassword(String username);
	
	public boolean ValidateUser(String username);

	public String GeneratePassword();

}
