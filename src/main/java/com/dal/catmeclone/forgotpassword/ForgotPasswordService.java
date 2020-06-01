package com.dal.catmeclone.forgotpassword;


public interface ForgotPasswordService {
	
	public boolean forgotpassword(String username);
	
	public boolean ValidateUser(String username);

	public String GeneratePassword();

}
