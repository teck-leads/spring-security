package com.techleads.app.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil_org {

	@Value("${jwt.secret}")
	private String secret;
	
	

	// 6. validate the username in token and database, expDate

	public boolean validateToken(String token, String username) {
		String tokenUserName = getUsername(token);
		return username.equals(tokenUserName) && !isTokenExpired(tokenUserName);
	}

	// 5. Validate Expiry date

	public boolean isTokenExpired(String token) {
		Date expDate = getExperiyDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}

	// 4. Read subject/username
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	// 3. Read exprity date
	public Date getExperiyDate(String token) {
		return getClaims(token).getExpiration();

	}

	// 2. Read claims
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
	}

	// 1. Generate Token
	public String generateToken(String subject) {

		String token = Jwts.builder().setSubject(subject).setIssuer("TeckLeadsIT")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();

		return token;
	}

}
