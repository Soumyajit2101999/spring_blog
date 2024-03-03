package com.blog.application.Implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.model.User;
import com.blog.application.payload.UserCreateRequest;
import com.blog.application.repository.UserRepository;
import com.blog.application.service.UserService;

@Service
public class UserServiceImplementation implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserCreateRequest createUser(UserCreateRequest user) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		User save = userRepository.save(dtoToUser(user));
		
		return UserToDto(save);
	}

	@Override
	public UserCreateRequest updateUser(UserCreateRequest user,Integer id) {
		// TODO Auto-generated method stub
		User findById = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", " Id ", id));
		
		findById.setId(id);
		findById.setEmail(user.getEmail());
		findById.setName(user.getName());
		findById.setPassword(user.getPassword());
		findById.setRoles(user.getRoles());
		
		User save = userRepository.save(findById);
		
		return UserToDto(save);
	}

	@Override
	public UserCreateRequest getUserById(Integer id) {
		// TODO Auto-generated method stub
		User findById = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", " Id ", id));
		return UserToDto(findById);
	}

	@Override
	public List<UserCreateRequest> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> findAll = userRepository.findAll();
		List<UserCreateRequest> collect = findAll.stream().map(user->this.UserToDto(user)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		User findById = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", " Id ", id));
		userRepository.delete(findById);
	}
	
	private User dtoToUser(UserCreateRequest userRequest)
	{
		User user = modelMapper.map(userRequest, User.class);
		
//		user.setId(userRequest.getId());
//		user.setEmail(userRequest.getEmail());
//		user.setName(userRequest.getName());
//		user.setPassword(userRequest.getPassword());
//		user.setRoles(userRequest.getRoles());
		
		return user;
	}
	
	private UserCreateRequest UserToDto(User user)
	{
		UserCreateRequest userCreateRequest = modelMapper.map(user, UserCreateRequest.class);
		return userCreateRequest;
	}

}
