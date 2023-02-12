package com.example.blog.payloads;

import com.example.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
	private Integer commentId;
	private String title;
	private String content;
	private User commentUser;
}
