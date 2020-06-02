package com.dal.catmeclone.UserProfile;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.model.User;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	@Value("${random}")
	String ALPHA_NUMERIC_STRING;
	final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);


	@Autowired
	ForgotPasswordDao forgotpasswordDb;
	
	public boolean forgotpassword(String username) {	
		boolean success;
		success= ValidateUser(username);
		return success;
	}
	
	@Override
	public boolean ValidateUser(String username) {
		try {
		if(forgotpasswordDb.checkexist(username)) {
			String password=GeneratePassword();
			forgotpasswordDb.UpdatePassword(username, password);
			logger.info("User:"+username+" is validated in forgot password service.");
			return true;
		}
		else {
			logger.error("User:"+username+" is not validated in forgot password service.");
			return false;
		}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public String GeneratePassword() {	
			
		StringBuilder builder = new StringBuilder();
		builder.setLength(0);

		for(int i=0;i<8;i++)
		{
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		String new_password= builder.toString();
		return new_password;
	}

}
