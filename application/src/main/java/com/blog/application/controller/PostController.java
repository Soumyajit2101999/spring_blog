package com.blog.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.application.config.AppConstants;
import com.blog.application.payload.ApiResponse;
import com.blog.application.payload.CategoryCreateRequest;
import com.blog.application.payload.PostCreateRequest;
import com.blog.application.payload.PostResponse;
import com.blog.application.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	PostService postService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostMapping("/add-post")
	@RolesAllowed({"ROLE_ADMIN","ROLE_AUTHOR"})
	public ResponseEntity<ApiResponse> createPost(@RequestParam String postData, @RequestParam("file") MultipartFile file)
	{
		ApiResponse apiResponse = new ApiResponse();
		
		if(file.isEmpty())
		{
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Request must contain file");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
		}
		if(!file.getContentType().equals("image/jpeg"))
		{
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Only JPEG content Types are allowed");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
		}
		
		PostCreateRequest postCreateRequest = null;
		try {
			postCreateRequest = objectMapper.readValue(postData, PostCreateRequest.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Something went Wrong");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Something went Wrong");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
		}
		
			boolean createPost = postService.createPost(postCreateRequest, file);
			if(createPost)
			{
				apiResponse.setSuccess(true);
				apiResponse.setMessage("Data successfuly inserted");
				return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
			}
			else
			{
				apiResponse.setSuccess(false);
				apiResponse.setMessage("Something went Wrong");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
			}
			
		
	}
	
	
	@PutMapping("/update-post/{postId}")
	@RolesAllowed({"ROLE_ADMIN","ROLE_AUTHOR"})
	public ResponseEntity<PostCreateRequest> updatepost(@Valid @RequestBody PostCreateRequest postCreateRequest,@PathVariable("postId") Integer post_id)
	{
			PostCreateRequest updatePost = postService.updatePost(postCreateRequest,post_id);
			return ResponseEntity.status(HttpStatus.OK).body(updatePost);
		
	}
	
	@GetMapping("/all-posts")
	@RolesAllowed({"ROLE_ADMIN","ROLE_AUTHOR","ROLE_VIEWER"})
	public ResponseEntity<PostResponse> allPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value="pageSize", defaultValue = "5", required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
	{
		
		PostResponse allPosts = postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return ResponseEntity.status(HttpStatus.OK).body(allPosts);
	}
}
