package com.dal.catmeclone.UserProfile;

public interface ForgotPasswordService {

	public void Resetlink(String username) throws Exception;

	public boolean ValidateUser(String username) throws Exception;

	public String GenerateToken();

	public String validatetoken(String confirmationToken) throws Exception;

	public void setNewPassword(String username,String password) throws Exception;

}
