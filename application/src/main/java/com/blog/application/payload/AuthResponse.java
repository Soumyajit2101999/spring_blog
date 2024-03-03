package com.blog.application.payload;

public class AuthResponse {

	private String email;
	private String accesstoken;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public AuthResponse(String email, String accesstoken) {
		super();
		this.email = email;
		this.accesstoken = accesstoken;
	}
	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
