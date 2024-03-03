package com.blog.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.application.payload.CategoryCreateRequest;

@Service
public interface CategoryService {

	CategoryCreateRequest createCategory(CategoryCreateRequest category);
	CategoryCreateRequest updateCategory(CategoryCreateRequest category,Integer id);
	CategoryCreateRequest getCategoryById(Integer id);
	List<CategoryCreateRequest> getAllCategory();
	
	void deleteCategory(Integer id);
}
