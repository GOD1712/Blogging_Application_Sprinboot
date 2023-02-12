package com.example.blog.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.blog.config.AppConstants;
import com.example.blog.payloads.ApiResponse;
import com.example.blog.payloads.PostDto;
import com.example.blog.payloads.PostResponse;
import com.example.blog.services.FileService;
import com.example.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${image.folder.path}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto,@PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId){
		PostDto addedPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(addedPost,HttpStatus.CREATED);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatedPost);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return ResponseEntity.ok(new ApiResponse("Post was deleted successfully",true));
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required=false) String pageNumber, 
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required=false) String pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required=false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
		PostResponse postResponse = this.postService.getAllPosts(Integer.parseInt(pageNumber),Integer.parseInt(pageSize), sortBy, sortDir);
		return ResponseEntity.ok(postResponse);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto post = this.postService.getPostById(postId);
		return ResponseEntity.ok(post);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUserId(
			@PathVariable Integer userId, 
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required=false) Integer pageNumber, 
			@RequestParam(value="pageSize", defaultValue=AppConstants.PAGE_SIZE, required = false) Integer pageSize){
		PostResponse postResponse = this.postService.getPostsByUser(userId, pageNumber, pageSize);
		return ResponseEntity.ok(postResponse);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostByCategoryId(
			@PathVariable Integer categoryId, 
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, 
			@RequestParam(value = "pageSize", defaultValue=AppConstants.PAGE_SIZE, required = false) Integer pageSize){
		PostResponse postResponse = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize);
		return ResponseEntity.ok(postResponse);
	}
	
	@GetMapping("/keyword/{keyword}/posts")
	public ResponseEntity<PostResponse> searchPosts(
			@PathVariable String keyword, 
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, 
			@RequestParam(value = "pageSize", defaultValue=AppConstants.PAGE_SIZE, required = false) Integer pageSize){
		PostResponse postResponse = this.postService.searchPosts(keyword, pageNumber, pageSize);
		return ResponseEntity.ok(postResponse);
	}
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(
			@RequestParam("image") MultipartFile image, 
			@PathVariable Integer postId) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		String uploadedImageName = this.fileService.uploadImage(path, image);
		postDto.setImageName(uploadedImageName);
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatedPostDto);
	}
	
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream inputStream = this.fileService.fetchImage(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, response.getOutputStream());
	}
}
