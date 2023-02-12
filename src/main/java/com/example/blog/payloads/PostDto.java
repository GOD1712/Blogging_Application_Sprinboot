package com.example.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.blog.entities.Category;
import com.example.blog.entities.Comment;
import com.example.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	private Category category;
	private User user;
	private Set<Comment> comments = new HashSet<>();
}
