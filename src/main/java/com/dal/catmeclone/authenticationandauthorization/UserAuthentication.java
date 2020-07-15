package com.dal.catmeclone.authenticationandauthorization;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;
import com.dal.catmeclone.encrypt.EncryptAbstractFactory;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
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

@Component
public class UserAuthentication implements AuthenticationManager {

    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    AuthenticationAbstractFactory authenticationAbstractFactory = abstractFactory.createAuthenticationAbstractFactory();
    EncryptAbstractFactory encryptAbstractFactory = abstractFactory.createEncryptAbstractFactory();
    private AuthenticateUserDao validate;
    private BCryptPasswordEncryption passwordencoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String bannerId = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        validate = authenticationAbstractFactory.createAuthenticateUserDao();
        passwordencoder = encryptAbstractFactory.createBCryptPasswordEncryption();
        User flag = null;
        List<GrantedAuthority> authorize = new ArrayList<GrantedAuthority>();
        User user = new User();
        user.setBannerId(bannerId);
        user.setPassword(password);

        try {
            flag = validate.authenticateUser(user);
        } catch (UserDefinedSQLException e) {
            e.printStackTrace();
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
