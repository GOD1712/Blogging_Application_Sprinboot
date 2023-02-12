package com.example.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.blog.entities.Category;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.PostDto;
import com.example.blog.payloads.PostResponse;
import com.example.blog.repositories.CategoryRepo;
import com.example.blog.repositories.PostRepo;
import com.example.blog.repositories.UserRepo;
import com.example.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.jpg");
		post.setAddedDate(new Date());
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		post.setUser(user);
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		post.setCategory(category);
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post existingPost = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		existingPost.setTitle(postDto.getTitle());
		existingPost.setContent(postDto.getContent());
		existingPost.setImageName(postDto.getImageName());
		existingPost.setAddedDate(new Date());
		Post updatedPost = this.postRepo.save(existingPost);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post existingPost = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		this.postRepo.delete(existingPost);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDir.equalsIgnoreCase("asc") ? Sort.by(Direction.ASC, sortBy) : Sort.by(Direction.DESC, sortBy));
		Page<Post> page= this.postRepo.findAll(pageable);
		List<Post> posts = page.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse(postDtos,page.getNumber(),page.getSize(),page.getTotalElements(),page.getTotalPages(),page.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post existingPost = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		return this.modelMapper.map(existingPost, PostDto.class);
	}

	@Override
	public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Post> page = this.postRepo.findByUser(user, pageable);
		List<Post> posts = page.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse(postDtos,page.getNumber(),page.getSize(),page.getTotalElements(),page.getTotalPages(),page.isLast());
		return postResponse;
	}

	@Override
	public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Post> page = this.postRepo.findByCategory(category,pageable);
		List<Post> posts = page.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse(postDtos,page.getNumber(),page.getSize(),page.getTotalElements(),page.getTotalPages(),page.isLast());
		return postResponse;
	}

	@Override
	public PostResponse searchPosts(String keyword, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Post> page = this.postRepo.findByTitleContains(keyword,pageable);
		List<Post> posts = page.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse(postDtos,page.getNumber(),page.getSize(),page.getTotalElements(),page.getTotalPages(),page.isLast());
		return postResponse;
	}

}
