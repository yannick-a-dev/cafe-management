package com.cafemanagementproject.cafemanagementproject.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTGenerator {
	
	public String extractUsername(String token) {
		return extractClaims(token,Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String username, String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		return createToken(claims, username);
	}
	
	private String createToken(Map<String, Object> claims,String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
				.signWith(SignatureAlgorithm.HS256, SecurityConstants.JWT_SECRET).compact();
	}
	
//	public Boolean validateToken(String token,UserDetails userDetails) {
//		final String username = extractUsername(token);
//		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}
	
//	 public String generateToken(Authentication authentication) {
//	        String username = authentication.getName();
//	        Date currentDate = new Date();
//	        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
//
//	        String token = Jwts.builder()
//	                .setSubject(username)
//	                .setIssuedAt(new Date())
//	                .setExpiration(expireDate)
//	                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
//	                .compact();
//	        return token;
//	    }
//	
	   public boolean validateToken(String token,UserDetails userDetails) {
		   
		   final String username = extractUsername(token);
    	   return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	        try {
//	            Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
//	            return true;
//	        } catch (Exception ex) {
//	            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
//	        }
	    }

	    public String getUsernameFromJWT(String token) {
	        Claims claims = Jwts.parser()
	                .setSigningKey(SecurityConstants.JWT_SECRET)
	                .parseClaimsJws(token)
	                .getBody();
	        return claims.getSubject();
	    }

	 
}
