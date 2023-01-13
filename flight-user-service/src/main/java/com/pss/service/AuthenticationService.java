package com.pss.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pss.entity.AuthenticationResponse;
import com.pss.entity.Role;
import com.pss.entity.User;
import com.pss.jwt.JwtService;
import com.pss.repo.UserRepository;

@Service
public class AuthenticationService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AuthenticationResponse register(User newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		newUser.setRole(Role.USER);
		userRepo.saveAndFlush(newUser);
		
		return new AuthenticationResponse(jwtService.generateToken(newUser));
		
		
	}
	
	public AuthenticationResponse login(Map<String, String> request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.get("username"), request.get("password"))
				);
		
		User user = userRepo.findByEmailId(request.get("username")).orElseThrow(() -> new RuntimeException("User not found"));
		
		return new AuthenticationResponse(jwtService.generateToken(user));
	}
}
