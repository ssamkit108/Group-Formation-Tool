package com.dal.catmeclone.authenticationandauthorization;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class SuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.sendRedirect(routeurl(authentication));
	}

	private String routeurl(Authentication authentication) {
		Map<String,String> assigntargeturl = new HashMap<>();
		assigntargeturl.put("admin", "/admin/adminDashboard");
		assigntargeturl.put("user", "/home");
		final Collection<? extends GrantedAuthority> rights = authentication.getAuthorities();
		for (final GrantedAuthority grantedAuthority : rights) {
			String roleName = grantedAuthority.getAuthority();

			if(assigntargeturl.containsKey(roleName)) {
				
				return assigntargeturl.get(roleName);
			}
		}
		return null;
	}
}
