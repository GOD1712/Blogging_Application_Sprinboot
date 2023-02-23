package com.example.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.example.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	
	@NotEmpty
	@Size(min=3, message="Name should have more than 3 characters")
	private String name;
	
	@Email(message="Email address is invalid")
	private String email;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<Role> roles = new HashSet<>();
}
