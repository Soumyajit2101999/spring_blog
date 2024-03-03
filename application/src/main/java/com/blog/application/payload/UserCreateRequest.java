package com.blog.application.payload;

import java.util.HashSet;
import java.util.Set;

import com.blog.application.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class UserCreateRequest {

	private int id;
	@NotEmpty
	@Size(min = 4, message = "Name must be minimum of 4 character")
	private String name;
	@Email(message = "Email Address is not Valid ||")
	private String email;
//	@NotEmpty(message = "Atleast One should be assigned")
    private Set<Role> roles = new HashSet<>();
    @NotEmpty(message = "Give Password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$", message ="Minimum eight and maximum 10 characters, at least one uppercase letter, one lowercase letter, one number and one special character")
	private String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserCreateRequest(int id, String name, String email, Set<Role> roles, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles = roles;
		this.password = password;
	}
	public UserCreateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
