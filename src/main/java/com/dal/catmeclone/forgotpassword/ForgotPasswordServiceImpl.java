package com.dal.catmeclone.forgotpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dal.catmeclone.model.User;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	@Value("${random}")
	String ALPHA_NUMERIC_STRING;

	@Autowired
	ForgotPasswordDao forgotpasswordDb;
	
	public boolean forgotpassword(String username) {	
		boolean success;
		success= ValidateUser(username);
		return success;
	}
	
	@Override
	public boolean ValidateUser(String username) {
		
		if(forgotpasswordDb.checkexist(username)) {
			String password=GeneratePassword();
			forgotpasswordDb.UpdatePassword(username, password);
			return true;
		}
		else {
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
