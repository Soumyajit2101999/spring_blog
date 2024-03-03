package com.blog.application.payload;

import com.blog.application.model.Category;
import com.blog.application.model.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PostCreateRequest {

	private Integer postId;
	
	@NotEmpty(message = "Title must not be empty")
	private String title;
	
	@NotEmpty(message = "Content must not be empty")
	private String content;
	
	private String imagename;
	
	@NotNull(message = "Category Id must not be empty")
	private Integer category_id;
	
	private Integer user_id;
	
	private CategoryCreateRequest cat;
	
	private UserCreateRequest user_dto;

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public PostCreateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public PostCreateRequest(Integer postId, @NotEmpty(message = "Title must not be empty") String title,
			@NotEmpty(message = "Content must not be empty") String content, String imagename, Integer category_id,
			Integer user_id, CategoryCreateRequest cat, UserCreateRequest user_dto) {
		super();
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.imagename = imagename;
		this.category_id = category_id;
		this.user_id = user_id;
		this.cat = cat;
		this.user_dto = user_dto;
	}

	public CategoryCreateRequest getCat() {
		return cat;
	}

	public void setCat(CategoryCreateRequest cat) {
		this.cat = cat;
	}

	public UserCreateRequest getUser_dto() {
		return user_dto;
	}

	public void setUser_dto(UserCreateRequest user_dto) {
		this.user_dto = user_dto;
	}
	

	

	
}
