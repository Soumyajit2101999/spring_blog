package com.blog.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.component.JwtTokenUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserIdFromToken {

	@Autowired
    private JwtTokenUtil jwtUtil;
	
	@Autowired
	private HttpServletRequest request;
	
	public Integer id()
	{
		String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
		
		System.out.println("==============================");
		System.out.println(token);
		System.out.println("==============================");
		
		Claims claims = jwtUtil.parseClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        
        String[] jwtSubject = subject.split(",");
     
        Integer user_id = Integer.parseInt(jwtSubject[0]);
        
        return user_id;
	}
	
	public String email()
	{
		String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
		
		System.out.println("==============================");
		System.out.println(token);
		System.out.println("==============================");
		
		Claims claims = jwtUtil.parseClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        
        String[] jwtSubject = subject.split(",");
     
        String email = jwtSubject[1];
        
        return email;
	}
}
