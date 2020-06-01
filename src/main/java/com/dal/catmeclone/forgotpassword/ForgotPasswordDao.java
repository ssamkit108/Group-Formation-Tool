package com.dal.catmeclone.forgotpassword;

public interface ForgotPasswordDao {
	public boolean checkexist(String username);
	public void UpdatePassword(String BannerId,String password);
}
