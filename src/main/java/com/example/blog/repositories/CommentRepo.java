package com.example.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entities.Comment;
import com.example.blog.entities.Post;

public interface CommentRepo extends JpaRepository<Comment, Integer>{
	List<Comment> findByPost(Post post);
}
