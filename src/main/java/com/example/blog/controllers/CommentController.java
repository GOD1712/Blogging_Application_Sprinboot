package com.example.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.payloads.ApiResponse;
import com.example.blog.payloads.CommentDto;
import com.example.blog.services.CommentService;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/user/{userId}/comment")
	public ResponseEntity<CommentDto> addComment(
			@RequestBody CommentDto commentDto,
			@PathVariable Integer postId, 
			@PathVariable Integer userId){
		CommentDto newCommentDto = this.commentService.createComment(commentDto, postId, userId);
		return new ResponseEntity<>(newCommentDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return ResponseEntity.ok(new ApiResponse("Comment was deleted successfully",true));
	}
	
	@GetMapping("/post/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Integer postId){
		List<CommentDto> commentDtos = this.commentService.getCommentsByPost(postId);
		return ResponseEntity.ok(commentDtos);
	}
	
	@PutMapping("/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer commentId){
		CommentDto updatedCommentDto = this.commentService.updateComment(commentDto, commentId);
		return ResponseEntity.ok(updatedCommentDto);
	}
	
}
