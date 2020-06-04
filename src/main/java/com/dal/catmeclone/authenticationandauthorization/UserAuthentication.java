package com.dal.catmeclone.authenticationandauthorization;


import java.sql.SQLException;
import java.sql.SQLException;	
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

@Component
public class UserAuthentication implements AuthenticationManager{

	@Autowired
	private Interface_AuthenticateUserDao validate;
	private BCryptPasswordEncryption passwordencoder=new BCryptPasswordEncryption();
	//private static final String Admin_bannerId = "ADMIN0000";
	//private static final String Admin_bannerId = "B00100167";

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String bannerId = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();


		User flag= null;
		List<GrantedAuthority> authorize = new ArrayList<GrantedAuthority>();

		if (User.isBannerIDValid(bannerId))
		{
			User user = new User();
			user.setBannerId(bannerId);
			user.setPassword(password);

			try {
				flag=validate.authenticateUser(user);
			} catch (SQLException | UserDefinedSQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (flag != null)
			{
				if (passwordencoder.matches(user.getPassword(), flag.getPassword())) {
				//if (user.getPassword().equals(flag.getPassword())) {


					if (flag.getUserRoles().getRoleName().equals("Admin")){

						authorize.add(new SimpleGrantedAuthority("admin"));						

					}


					else if (flag.getUserRoles().getRoleName().equals("Guest")){
						authorize.add(new SimpleGrantedAuthority("user"));						

					}



				}
				else {
					throw new BadCredentialsException("Incorrect password");
				}

				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(bannerId,
						password, authorize);
				return token;

			}
		}


		return null;
	}

}
