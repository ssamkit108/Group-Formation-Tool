package com.dal.catmeclone.authenticationandauthorization;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.dal.catmeclone.SystemConfig;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	UserAuthentication authenticationManager;
	AuthenticationSuccessHandler successHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/signup", "/forgotpassword", "/", "/reset", "/reset-password", "/reset_password")
				.permitAll().and().authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("admin")
				.antMatchers("/courses", "/allcourses", "/mycourse/**", "/questionmanager/**").hasAnyAuthority("user").anyRequest()
				.authenticated().and().formLogin().loginPage("/login").permitAll().successHandler(authSuccessHandler())
				.and().logout().logoutUrl("/logout").permitAll().and().exceptionHandling()
				.accessDeniedPage("/access-denied");

	}

	public AuthenticationSuccessHandler authSuccessHandler() {
		successHandler = SystemConfig.instance().getAuthenticationSuccessHandler();
		return successHandler;
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		authenticationManager = SystemConfig.instance().getUserAuthentication();
		return authenticationManager;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

}
