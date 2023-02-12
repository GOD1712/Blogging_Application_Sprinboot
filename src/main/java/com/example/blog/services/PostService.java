package com.example.blog.services;

import com.example.blog.payloads.PostDto;
import com.example.blog.payloads.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	PostDto updatePost(PostDto postDto, Integer postId);
	void deletePost(Integer postId);
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	PostDto getPostById(Integer postId);
	PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize);
	PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
	PostResponse searchPosts(String keyword, Integer pageNumber, Integer pageSize);
}
