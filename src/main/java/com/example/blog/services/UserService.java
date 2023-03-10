package com.example.blog.services;

import java.util.List;

import com.example.blog.payloads.PasswordDto;
import com.example.blog.payloads.UserDto;

public interface UserService {
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer id);
	UserDto getUserById(Integer id);
	List<UserDto> getAllUsers();
	void deleteUser(Integer id);
	UserDto getUserByEmail(String email);
	UserDto changePassword(PasswordDto passwordDto);
}
