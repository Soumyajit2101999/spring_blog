package com.blog.application.payload;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class AuthRequest {

	@Email(message = "Please give proper Email !!")
	@NotEmpty(message = "Plaese Give Email")
	private String email;
	
	@NotEmpty(message = "Plaese Give Password")
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
