package com.dal.catmeclone.UserProfile;

public interface ForgotPasswordService {

    public void resetlink(String username) throws Exception;

    public String generateToken();

    public String validateToken(String confirmationToken) throws Exception;

    public void setNewPassword(String username, String password) throws Exception;

}
