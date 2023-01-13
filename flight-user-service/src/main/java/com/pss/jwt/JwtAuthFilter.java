package com.pss.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Get request header
		final String authHeader = request.getHeader("Authorization");

		// Check if header is null or if it contains jwt token i.e. bearer token if
		// false return early
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// extract jwt token from header
		final String jwtToken = authHeader.substring(7);

		System.out.println("Token = " + jwtToken);

		// extract username i.e. emailId from jwt token
		final String userEmail = jwtService.extractUsername(jwtToken);

		// check if username i.e emailId is null or not
		// if username is not null check securityContextHolder is null or not
		// if securityContextHolder is null update securityContextHolder
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// as security context holder is null
			// get user from database
			UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

			// Check if jwt token is valid or not
			if (jwtService.isTokenValid(jwtToken, userDetails)) {
				// Create username password authentication token to update security context
				// holder
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				// Add request details to authToken
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Now update the security context holder
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		// now do filtering
		filterChain.doFilter(request, response);

	}

}
