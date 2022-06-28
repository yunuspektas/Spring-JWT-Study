package com.tpe.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final Logger logger=LoggerFactory.getLogger(AuthTokenFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwtToken= parseJwt(request);
		
		try {
			if(jwtToken!=null && jwtUtils.validateToken(jwtToken)) {
				String userName= jwtUtils.getUserNameFromJwtToken(jwtToken);
				
				UserDetails userDetails=userDetailsService.loadUserByUsername(userName);
				//request.setAttribute("userName", userName);
				UsernamePasswordAuthenticationToken authentication=
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}catch(Exception e) {
			logger.error("User not found {}",e.getMessage());
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String parseJwt(HttpServletRequest request) {
		String header=request.getHeader("Authorization");
		if(StringUtils.hasText(header)&&header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null;
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		AntPathMatcher antMatcher=new AntPathMatcher();
		return antMatcher.match("/register", request.getServletPath())||
				antMatcher.match("/login", request.getServletPath());
	}
	

}
