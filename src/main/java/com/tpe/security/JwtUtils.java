package com.tpe.security;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.tpe.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	
	private static final Logger logger=LoggerFactory.getLogger(JwtUtils.class);
	
	
	private String jwtSecret="batch60";
	
	//24*60*60*1000
	private long jwtExpirationMs=86400000;
	
	
	public String generateToken(Authentication authentication) {
	   UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
	   
	   return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
			   .setExpiration(new Date(new Date().getTime()+jwtExpirationMs))
			   .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	 
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}catch (ExpiredJwtException e) {
			logger.error("JWT Token expired {}",e.getMessage());
		}
		catch (UnsupportedJwtException e) {
			logger.error("JWT Token unsupported {}",e.getMessage());
		}
		catch (MalformedJwtException e) {
			logger.error("JWT Token Malformed {}",e.getMessage());
		}
		catch (SignatureException e) {
			logger.error("JWT Token Invalid Signature {}",e.getMessage());
		}
		catch (IllegalArgumentException e) {
			logger.error("JWT Illegal argument{}",e.getMessage());
		}
		
		return false;
	}
	
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	

}
