package com.example.blog.services;

import java.util.List;

import com.example.blog.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
	void deleteComment(Integer commentId);
	List<CommentDto> getCommentsByPost(Integer postId);
	CommentDto updateComment(CommentDto commentDto, Integer commentId);
}
