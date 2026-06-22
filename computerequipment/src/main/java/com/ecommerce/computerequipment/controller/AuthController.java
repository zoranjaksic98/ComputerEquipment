package com.ecommerce.computerequipment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecommerce.computerequipment.model.JwtRequest;
import com.ecommerce.computerequipment.model.JwtResponse;
import com.ecommerce.computerequipment.security.JwtHelper;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final UserDetailsService userDetailsService;
	
	private final AuthenticationManager manager;
	
	private final JwtHelper jwtHelper;
	
	public AuthController(UserDetailsService userDetailsService, AuthenticationManager manager,JwtHelper jwtHelper) {
		this.userDetailsService = userDetailsService;
		this.manager = manager;
		this.jwtHelper = jwtHelper;
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtHelper.generateToken(userDetails);
		JwtResponse response = JwtResponse.builder()
				.username(userDetails.getUsername())
				.token(token)
				.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<UserDetails> getUserDetails(@RequestParam("Authorization") String tokenHeader){
		String token = extractTokenFromHeader(tokenHeader);
		if(token != null) {
			String username = jwtHelper.getUserNameFromToken(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			return new ResponseEntity<>(userDetails, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	private String extractTokenFromHeader(String tokenHeader) {
		if(tokenHeader != null && tokenHeader.startsWith("Bearer")) {
			return tokenHeader.substring(7);
		}
		return null;
	}
	
	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken autehnticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			manager.authenticate(autehnticationToken);
		} catch(BadCredentialsException ex) {
			throw new BadCredentialsException("Invalid Username od Password");
		}
	}
}
