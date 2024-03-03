package com.blog.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.application.payload.PostCreateRequest;
import com.blog.application.payload.PostResponse;
import com.blog.application.payload.UserCreateRequest;

@Service
public interface PostService {

	boolean createPost(PostCreateRequest post,MultipartFile file);
	PostCreateRequest updatePost(PostCreateRequest post,Integer id);
	PostCreateRequest getPostById(Integer id);
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	List<PostCreateRequest> getPostByCategory(Integer categoryId);
	List<PostCreateRequest> getPostByUser(Integer userId);
	List<PostCreateRequest> searchPost (String keywords);	
	
	void deletePost(Integer id);
}
