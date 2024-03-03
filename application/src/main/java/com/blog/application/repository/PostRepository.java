package com.blog.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.application.model.Category;
import com.blog.application.model.Post;
import com.blog.application.model.User;

public interface PostRepository extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
}
