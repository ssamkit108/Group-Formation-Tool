package com.dal.catmeclone.UserProfile;

import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DatabaseConnectionImpl;
import java.util.UUID;


public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	final Logger logger = LoggerFactory.getLogger(ForgotPasswordServiceImpl.class);

	ForgotPasswordDao forgotpasswordDb;
	
	
	public void Resetlink(String username) throws Exception {	
		try {
		forgotpasswordDb=SystemConfig.instance().getForgotPasswordDao();

		String token=GenerateToken();
		
		//This will update token in database and send mail to user
		forgotpasswordDb.UpdateToken(username, token);
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public boolean ValidateUser(String username) throws Exception {
		try {
			forgotpasswordDb=SystemConfig.instance().getForgotPasswordDao();
			if(forgotpasswordDb.checkexist(username)) {
				logger.info("Banner Id:"+username+" validated in successfully.");
				return true;
			}
			else {
				logger.error("User:"+username+" is not validated in forgot password service.");
				throw new Exception("Banner Id:"+username+" does not exist in our system.");
			}
		}
		catch(Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage());
		}
	}

	public String GenerateToken() {    
        return UUID.randomUUID().toString();
    }

	@Override
	public String validatetoken(String confirmationToken) throws Exception {
		try {
		forgotpasswordDb=SystemConfig.instance().getForgotPasswordDao();

		String bannerid=forgotpasswordDb.checktokenexist(confirmationToken);
		if(!bannerid.isEmpty() && bannerid!=null) {
			return bannerid;
		}
		else {
			return "";
		}
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void NewPassword(String username, String password) throws Exception {
	try {
		forgotpasswordDb=SystemConfig.instance().getForgotPasswordDao();
		if(password.length()<8) {
			throw new Exception("Please enter a valid password");
		}
		forgotpasswordDb.SetNewPassword(username, password);
	}catch(SQLException e) {
		throw new SQLException(e.getMessage());
	}
	catch(Exception e) {
		throw new Exception(e.getLocalizedMessage());
	}
	}
}
