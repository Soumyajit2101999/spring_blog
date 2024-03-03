package com.blog.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.application.payload.UserCreateRequest;

@Service
public interface UserService {

	UserCreateRequest createUser(UserCreateRequest user);
	UserCreateRequest updateUser(UserCreateRequest user,Integer id);
	UserCreateRequest getUserById(Integer id);
	List<UserCreateRequest> getAllUsers();
	
	void deleteUser(Integer id);
}
