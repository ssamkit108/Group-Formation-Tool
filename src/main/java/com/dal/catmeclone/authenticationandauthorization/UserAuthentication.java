package com.dal.catmeclone.authenticationandauthorization;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;
import com.dal.catmeclone.encrypt.EncryptAbstractFactory;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class UserAuthentication implements AuthenticationManager {

	private Logger LOGGER = Logger.getLogger(UserAuthentication.class.getName());
	AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	AuthenticationAbstractFactory authenticationAbstractFactory = abstractFactory.createAuthenticationAbstractFactory();
	EncryptAbstractFactory encryptAbstractFactory = abstractFactory.createEncryptAbstractFactory();
	ModelAbstractFactory modelAbstractFactory = abstractFactory.createModelAbstractFactory();
	private AuthenticateUserDao authenticateUserDao;
	private BCryptPasswordEncryption passwordencoder;

	
	public UserAuthentication() {
		super();
		authenticateUserDao = authenticationAbstractFactory.createAuthenticateUserDao();
	}
	
	
	public UserAuthentication(AuthenticateUserDao authenticateUserDao) {
		super();
		this.authenticateUserDao = authenticateUserDao;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String bannerId = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		
		passwordencoder = encryptAbstractFactory.createBCryptPasswordEncryption();
		User flag = null;
		List<GrantedAuthority> authorize = new ArrayList<GrantedAuthority>();
		User user = modelAbstractFactory.createUser();
		user.setBannerId(bannerId);
		user.setPassword(password);

		try {
			flag = authenticateUserDao.authenticateUser(user);
		} catch  (UserDefinedException e) {
			LOGGER.warning("Error Occured while authentication: "+e.getMessage());
//			throw new UserDefinedException("DATABASE ERROR occurred. Please try after some time.");
		}
		
		if (flag != null) {
			if (passwordencoder.matches(user.getPassword(), flag.getPassword())) {
				if (flag.getUserRoles().getRoleName().equals("Admin")) {
					authorize.add(new SimpleGrantedAuthority("admin"));
				} else if (flag.getUserRoles().getRoleName().equals("Guest")) {
					authorize.add(new SimpleGrantedAuthority("user"));
				}
			} else {
				throw new BadCredentialsException("Incorrect password");
			}

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(bannerId, password,
					authorize);
			return token;
		}
		return null;
	}
}
