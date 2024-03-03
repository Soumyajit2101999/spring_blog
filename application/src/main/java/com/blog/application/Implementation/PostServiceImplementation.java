package com.blog.application.Implementation;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.application.component.JwtTokenUtil;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.model.Category;
import com.blog.application.model.Post;
import com.blog.application.model.Role;
import com.blog.application.model.User;
import com.blog.application.payload.CategoryCreateRequest;
import com.blog.application.payload.PostCreateRequest;
import com.blog.application.payload.PostResponse;
import com.blog.application.repository.CategoryRepository;
import com.blog.application.repository.PostRepository;
import com.blog.application.repository.UserRepository;
import com.blog.application.service.PostService;
import com.blog.application.service.UserIdFromToken;
import com.blog.application.component.FileUploadHelper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

@Service
public class PostServiceImplementation implements PostService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserIdFromToken userIdFromToken;
	
	@Autowired
	private FileUploadHelper fileUploadHelper;
	
	@Override
	public boolean createPost(PostCreateRequest post, MultipartFile file) {
		// TODO Auto-generated method stub
		Category categoryFindById = categoryRepository.findById(post.getCategory_id()).orElseThrow(()-> new ResourceNotFoundException("Category", " Id ", post.getCategory_id()));
		
        Integer user_id = userIdFromToken.id();
        
        User userFindById = userRepository.findById(user_id).orElseThrow(()-> new ResourceNotFoundException("User ", " Id ", user_id));
		
        boolean uploadFile = fileUploadHelper.uploadFile(file);
        
        if(uploadFile)
        {
		Post dtoToPost = dtoToPost(post);
		dtoToPost.setImagename(file.getOriginalFilename());
		dtoToPost.setCreated_at(new Date());
		dtoToPost.setCreated_by(user_id);
		dtoToPost.setUpdated_at(new Date());
		dtoToPost.setUpdated_by(user_id);
		dtoToPost.setCategory(categoryFindById);
		dtoToPost.setUser(userFindById);
		Post save = postRepository.save(dtoToPost);
		return true;
        }
        else
        {
        	return false;
        }
	}

	@Override
	public PostCreateRequest updatePost(PostCreateRequest post, Integer id) {
		// TODO Auto-generated method stub
		Post findPost = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Posts ", " id ", id));
		Category findCategory = categoryRepository.findById(post.getCategory_id()).orElseThrow(()-> new ResourceNotFoundException("Category ", " id ", post.getCategory_id()));        
        User userFindById = userRepository.findById(post.getUser_id()).orElseThrow(()-> new ResourceNotFoundException("User ", " Id ", post.getUser_id()));
        
		findPost.setPostId(id);
		findPost.setTitle(post.getTitle());
		findPost.setContent(post.getContent());
		findPost.setImagename("something.png");
		findPost.setCategory(findCategory);
		findPost.setUser(userFindById);
		findPost.setUpdated_at(new Date());
		findPost.setUpdated_by(post.getUser_id());
		
		Post save = postRepository.save(findPost);
		
		return PostToDto(save);
	}

	@Override
	public PostCreateRequest getPostById(Integer id) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Posts ", " id ", id));
		return PostToDto(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort = Sort.by(sortBy).ascending();
		}
		else
		{
			sort = Sort.by(sortBy).descending();
		}
		Pageable p = PageRequest.of(pageNumber,pageSize,sort);
		
		Page<Post> pagePost = postRepository.findAll(p);
		List<Post> findAll = pagePost.getContent();
		List<PostCreateRequest> collect = findAll.stream().map(post->this.PostToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(collect);
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		
		return postResponse;
	}

	@Override
	public List<PostCreateRequest> getPostByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category findCategory = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", " id ", categoryId));
		List<Post> findByCategory = postRepository.findByCategory(findCategory);
		List<PostCreateRequest> collect = findByCategory.stream().map(post->this.PostToDto(post)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<PostCreateRequest> getPostByUser(Integer userId) {
		// TODO Auto-generated method stub
		User userFindById = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ", " Id ", userId));
		List<Post> findByUser = postRepository.findByUser(userFindById);
		List<PostCreateRequest> collect = findByUser.stream().map(post->this.PostToDto(post)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<PostCreateRequest> searchPost(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePost(Integer id) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Posts ", " id ", id));
		postRepository.delete(post);
	}
	
	private Post dtoToPost(PostCreateRequest postRequest)
	{
		Post post = modelMapper.map(postRequest, Post.class);
		return post;
	}
	
	private PostCreateRequest PostToDto(Post post)
	{
		PostCreateRequest postCreateRequest = modelMapper.map(post, PostCreateRequest.class);
		return postCreateRequest;
	}

}
