package com.techleads.app.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.techleads.app.util.JWTUtil;
@Component
public class SecurityFilter extends  OncePerRequestFilter{
	@Autowired
	private JWTUtil jWTUtil;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//1. read token from Authorization header
		//String token = request.getHeader("Authorization");
		String token = request.getHeader("Authorization");
		
		if(!StringUtils.isEmpty(token)) {
			
			String username = jWTUtil.getUsername(token);
			
			//Username should not be empty and context-auth must be empty
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(username);
				
				
				//validate token
				boolean validateToken = jWTUtil.validateToken(token, loadUserByUsername.getUsername());
				if(validateToken) {
					UsernamePasswordAuthenticationToken authenticationToken
					=new UsernamePasswordAuthenticationToken(
							username, loadUserByUsername.getPassword(), loadUserByUsername.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
				
					//final object stored in SecurityContext with user details
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
				
			}
		}
		filterChain.doFilter(request, response);
	}

}
