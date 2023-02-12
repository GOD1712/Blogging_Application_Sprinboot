package com.example.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entities.Comment;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.CommentDto;
import com.example.blog.repositories.CommentRepo;
import com.example.blog.repositories.PostRepo;
import com.example.blog.repositories.UserRepo;
import com.example.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		Comment newComment = this.modelMapper.map(commentDto, Comment.class);
		newComment.setPost(post);
		newComment.setCommentUser(user);
		Comment savedComment = this.commentRepo.save(newComment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
		this.commentRepo.delete(comment);
	}

	@Override
	public List<CommentDto> getCommentsByPost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		List<Comment> comments = this.commentRepo.findByPost(post);
		List<CommentDto> commentDtos = comments.stream().map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		return commentDtos;
	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
		comment.setTitle(commentDto.getTitle());
		comment.setContent(commentDto.getContent());
		Comment updatedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(updatedComment, CommentDto.class);
	}
}
