package com.blog.application.payload;

import jakarta.validation.constraints.NotEmpty;

public class CategoryCreateRequest {

	private Integer categoryId;
	@NotEmpty(message = "Title must not be empty")
	private String categoryTitle;
	private String categoryDescription;
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryTitle() {
		return categoryTitle;
	}
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public CategoryCreateRequest(Integer categoryId,
			@NotEmpty(message = "Title must not be empty") String categoryTitle, String categoryDescription) {
		super();
		this.categoryId = categoryId;
		this.categoryTitle = categoryTitle;
		this.categoryDescription = categoryDescription;
	}
	public CategoryCreateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
