/**
 * 
 */
package com.dal.catmeclone.authenticationandauthorization;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

/**
 * @author Mayank
 *
 */
@Configuration
@EnableWebSecurity
public class AuthenticationServer extends WebSecurityConfigurerAdapter {
	
	
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
	
		
		UserBuilder users = User.withDefaultPasswordEncoder();
		
		auth.inMemoryAuthentication()
			.withUser(users.username("john").password("test123").roles("Guest"))
			.withUser(users.username("B00123456").password("test123").roles("Guest"))
			.withUser(users.username("susan").password("test123").roles("Admin"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		
		/*http
		.csrf().disable()
		.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/authenticate")
		.permitAll();*/
	
		http.csrf().disable()
    			.authorizeRequests()
    			.antMatchers("/admin/**").hasAnyRole("ADMIN")
    			.antMatchers("/courseinstructor/**").hasAnyRole("Instructor")
    			.and()
    			.authorizeRequests()
    			.anyRequest().authenticated()
			    .and()
			    .formLogin()
			    //custom login page
			    .loginPage("/login")
			    .loginProcessingUrl("/authenticate")//url to submit username and password
			    .successForwardUrl("/")//landing page after successful login     
			    .and()
		        .logout()
		        .logoutUrl("/logout") 
		        .logoutSuccessUrl("/login")
		        .permitAll()
		        .and()
		        .csrf().disable();
}
	
	
	 	
		

	
}
