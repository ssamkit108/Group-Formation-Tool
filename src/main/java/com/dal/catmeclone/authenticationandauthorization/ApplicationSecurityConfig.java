package com.dal.catmeclone.authenticationandauthorization;



import org.springframework.beans.factory.annotation.Autowired;	
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserAuthentication authenticationManager;
	
	@Autowired
	SuccessHandler authenticationSuccessHandler;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/signup","/forgotpassword","/").permitAll().and().authorizeRequests()
		.antMatchers("/admin/**").hasAnyAuthority("admin")
		.antMatchers("/courses","/allcourses").hasAnyAuthority("user")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login").permitAll()
		.successHandler(authSuccessHandler())
		.and()
		.logout()
		.logoutUrl("/logout").permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied");




	}
	
	public AuthenticationSuccessHandler authSuccessHandler() {
		return authenticationSuccessHandler;
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return authenticationManager;
	}

	@Override
	public void configure(WebSecurity web) throws Exception
	{
		web.ignoring().antMatchers("/resources/**");
	}


}
