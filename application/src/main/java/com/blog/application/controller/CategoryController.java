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

import com.blog.application.payload.CategoryCreateRequest;
import com.blog.application.payload.UserCreateRequest;
import com.blog.application.service.CategoryService;
import com.blog.application.service.UserService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/add-category")
	@RolesAllowed({"ROLE_ADMIN"})
	public ResponseEntity<CategoryCreateRequest> createCategory(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest)
	{
		try
		{
			CategoryCreateRequest createRequest = categoryService.createCategory(categoryCreateRequest);
			return ResponseEntity.status(HttpStatus.CREATED).body(createRequest);
		}
		catch(Exception e)
		{
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@PutMapping("/update-category/{categoryId}")
	@RolesAllowed({"ROLE_ADMIN"})
	public ResponseEntity<CategoryCreateRequest> updateCategory(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest,@PathVariable Integer category_id)
	{
		try
		{
			CategoryCreateRequest updateCategory = categoryService.updateCategory(categoryCreateRequest,category_id);
			return ResponseEntity.status(HttpStatus.OK).body(updateCategory);
		}
		catch(Exception e)
		{
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@DeleteMapping("/delete-category/{categoryId}")
	@RolesAllowed({"ROLE_ADMIN"})
	public ResponseEntity<?> deleteUser(@PathVariable Integer id)
	{
		try
		{
			categoryService.deleteCategory(id);
			return ResponseEntity.status(HttpStatus.OK).body("Category Deleted Successfuly");
		}
		catch(Exception e)
		{
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/all-category")
	public ResponseEntity<List<CategoryCreateRequest>> category()
	{
		try
		{
			List<CategoryCreateRequest> allCategory = categoryService.getAllCategory();
			return ResponseEntity.status(HttpStatus.OK).body(allCategory);
		}
		catch(Exception e)
		{
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@GetMapping("/one-category/{categoryId}")
	public ResponseEntity<CategoryCreateRequest> category(@PathVariable("categoryId") Integer id)
	{
		
			CategoryCreateRequest categories = categoryService.getCategoryById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(categories);
		
	}
	
}
