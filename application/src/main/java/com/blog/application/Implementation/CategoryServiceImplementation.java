package com.blog.application.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.model.Category;
import com.blog.application.model.User;
import com.blog.application.payload.CategoryCreateRequest;
import com.blog.application.payload.UserCreateRequest;
import com.blog.application.repository.CategoryRepository;
import com.blog.application.service.CategoryService;

@Service
public class CategoryServiceImplementation implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryCreateRequest createCategory(CategoryCreateRequest category) {
		// TODO Auto-generated method stub
		Category save = categoryRepository.save(dtoToCategory(category));
		
		return CategoryToDto(save);
	}

	@Override
	public CategoryCreateRequest updateCategory(CategoryCreateRequest category, Integer id) {
		// TODO Auto-generated method stub
		Category findById = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", " Id ", id));
		
		findById.setCategoryId(id);
		findById.setCategoryTitle(category.getCategoryTitle());
		findById.setCategoryDescription(category.getCategoryDescription());
		
		Category save = categoryRepository.save(findById);
		
		return CategoryToDto(save);
	}

	@Override
	public CategoryCreateRequest getCategoryById(Integer id) {
		// TODO Auto-generated method stub
		Category findById = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", " Id ", id));
		return CategoryToDto(findById);
	}

	@Override
	public List<CategoryCreateRequest> getAllCategory() {
		// TODO Auto-generated method stub
		List<Category> findAll = categoryRepository.findAll();
		List<CategoryCreateRequest> collect = findAll.stream().map(category->this.CategoryToDto(category)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deleteCategory(Integer id) {
		// TODO Auto-generated method stub
		Category findById = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", " Id ", id));
		categoryRepository.delete(findById);
	}
	
	private Category dtoToCategory(CategoryCreateRequest categoryRequest)
	{
		Category category = modelMapper.map(categoryRequest, Category.class);
		return category;
	}
	
	private CategoryCreateRequest CategoryToDto(Category category)
	{
		CategoryCreateRequest categoryCreateRequest = modelMapper.map(category, CategoryCreateRequest.class);
		return categoryCreateRequest;
	}

}
