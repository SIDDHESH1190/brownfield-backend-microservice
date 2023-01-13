package com.pss.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	// create secret of minimum 256 bit
	private static final String SECRET_KEY = "4B6150645367566B59703373367639792442264529482B4D6251655468576D5A";

	// Extract username i.e emailId from jwtToken
	// Claims::getSubject is the username for the claims
	// subject is username in this context
	public String extractUsername(String jwtToken) {
		return extractClaim(jwtToken, Claims::getSubject);
	}

	// method to generate jwtToken from extra claims and user details
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	// method to generate jwtToken from userDetails
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<String, Object>(), userDetails);
	}

	// extract one claim from all claims
	public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(jwtToken);
		return claimsResolver.apply(claims);
	}

	// extract all claims from jwtToken
	// This will let us know that user is claiming who he is and the the data is not
	// change in between
	public Claims extractAllClaims(String jwtToken) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(jwtToken)
				.getBody();
	}

	// Create key to sign in
	public Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// check if token is valid or not
	public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
		return (extractUsername(jwtToken).equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
	}

	// check if token is expired or not
	public boolean isTokenExpired(String jwtToken) {
		return extractExpiration(jwtToken).before(new Date());
	}

	// extract Expiration from jwt Token
	public Date extractExpiration(String jwtToken) {
		return extractClaim(jwtToken, Claims::getExpiration);
	}

}
