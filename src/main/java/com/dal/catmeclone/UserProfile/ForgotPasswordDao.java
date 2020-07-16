package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

public interface ForgotPasswordDao {
    public boolean checkexist(String username) throws UserDefinedSQLException, Exception;

    public void updateToken(String BannerId, String token) throws Exception;

    void setNewPassword(String BannerId, String password) throws Exception;

    public String checktokenexist(String token) throws Exception;
}
