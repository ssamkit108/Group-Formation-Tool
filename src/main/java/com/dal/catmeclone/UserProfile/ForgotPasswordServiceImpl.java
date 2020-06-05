package com.dal.catmeclone.UserProfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.dal.catmeclone.DBUtility.DatabaseConnection;


@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	
	final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);


	@Autowired
	ForgotPasswordDao forgotpasswordDb;
	
	@Value("${random}")
    String ALPHA_NUMERIC_STRING;
	
	
	

	public boolean forgotpassword(String username) throws Exception {	
		boolean success;
		success= ValidateUser(username);
		return success;
	}
	
	@Override
	public boolean ValidateUser(String username) throws Exception {
		try {
		if(username.length()<10)
		{
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
		else {
			logger.error("User:"+username+" length should be less than 9.");
			return false;
		}
		}
		catch(Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
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
