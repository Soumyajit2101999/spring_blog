package com.blog.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.component.JwtTokenUtil;
import com.blog.application.payload.AuthRequest;
import com.blog.application.payload.AuthResponse;
import com.blog.application.model.User;

import jakarta.validation.Valid;

@RestController
public class AuthApi {

	@Autowired
	AuthenticationManager authenticationManager;
	private Authentication authenticate;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request)
	{
		try
		{
			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			User user = (User) authenticate.getPrincipal();
			String accessToken = jwtTokenUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getEmail(), accessToken);
            
            return ResponseEntity.ok().body(response);
		}
		catch(BadCredentialsException ex)
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
