package com.blog.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.payload.UserCreateRequest;
import com.blog.application.service.UserService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	@RolesAllowed({"ROLE_ADMIN"})
	public ResponseEntity<UserCreateRequest> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest)
	{
		try
		{
			UserCreateRequest createUser = userService.createUser(userCreateRequest);
			return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
		}
		catch(Exception e)
		{
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@PutMapping("/update/{userId}")
	@RolesAllowed({"ROLE_ADMIN"})
	public ResponseEntity<UserCreateRequest> updateUser(@Valid @RequestBody UserCreateRequest userCreateRequest,@PathVariable Integer user_id)
	{
		try
		{
			UserCreateRequest updateUser = userService.updateUser(userCreateRequest,user_id);
			return ResponseEntity.status(HttpStatus.OK).body(updateUser);
		}
		catch(Exception e)
		{
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@DeleteMapping("/delete/{userId}")
	@RolesAllowed({"ROLE_ADMIN"})
	public ResponseEntity<?> deleteUser(@PathVariable Integer id)
	{
		try
		{
			userService.deleteUser(id);
			return ResponseEntity.status(HttpStatus.OK).body("User Deleted Successfuly");
		}
		catch(Exception e)
		{
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/all-user")
	public ResponseEntity<List<UserCreateRequest>> users()
	{
		try
		{
			List<UserCreateRequest> allUsers = userService.getAllUsers();
			return ResponseEntity.status(HttpStatus.OK).body(allUsers);
		}
		catch(Exception e)
		{
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@GetMapping("/one-user/{userId}")
	public ResponseEntity<UserCreateRequest> users(@PathVariable Integer id)
	{
		try
		{
			UserCreateRequest user = userService.getUserById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
		catch(Exception e)
		{
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	
}
