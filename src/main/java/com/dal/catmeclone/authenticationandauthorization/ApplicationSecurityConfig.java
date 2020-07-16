package com.dal.catmeclone.authenticationandauthorization;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    AuthenticationAbstractFactory authenticationAbstractFactory = abstractFactory.createAuthenticationAbstractFactory();
    UserAuthentication authenticationManager;
    AuthenticationSuccessHandler successHandler;
    SimpleUrlAuthenticationFailureHandler failureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/signup", "/forgotpassword", "/", "/reset", "/reset-password", "/reset_password", "/login")
                .permitAll().and().authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("admin")
                .antMatchers("/courses", "/allcourses", "/mycourse/**", "/questionmanager/**", "/survey/**", "/responsepage/**")
                .hasAnyAuthority("user").anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
                .failureHandler(customFailureHandler()).permitAll()
                .successHandler(authSuccessHandler()).permitAll()
                .and().logout().logoutUrl("/logout").permitAll().and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }


    public SimpleUrlAuthenticationFailureHandler customFailureHandler() {
        failureHandler = new FailureHandler();
        return failureHandler;
    }

    public AuthenticationSuccessHandler authSuccessHandler() {
        successHandler = authenticationAbstractFactory.createSuccessHandler();
        return successHandler;
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        authenticationManager = authenticationAbstractFactory.createUserAuthentication();
        return authenticationManager;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

}
