package com.example.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blog.config.AppConstants;
import com.example.blog.entities.Role;
import com.example.blog.entities.User;
import com.example.blog.exceptions.ResNotFoundException;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.PasswordDto;
import com.example.blog.payloads.UserDto;
import com.example.blog.repositories.RoleRepo;
import com.example.blog.repositories.UserRepo;
import com.example.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto user) {
		User newUser = dtoToUser(user);
		newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
		Role normalUserRole = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		newUser.getRoles().add(normalUserRole);
		User savedUser = this.userRepo.save(newUser);
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto user, Integer id) {
		User existingUser = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		
		existingUser.setAbout(user.getAbout());
		existingUser.setEmail(user.getEmail());
		existingUser.setName(user.getName());
		existingUser.setPassword(user.getPassword());
		//existingUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		User updatedUser = this.userRepo.save(existingUser);
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User","Id",id));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User","Id",id));
		this.userRepo.delete(user);
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto getUserByEmail(String email) {
		User user = this.userRepo.findByEmail(email).orElseThrow(() -> new ResNotFoundException("User", "email", email));
		return this.modelMapper.map(user,UserDto.class);
	}

	@Override
	public UserDto changePassword(PasswordDto passwordDto) {
		User user = this.userRepo.findByEmail(passwordDto.getUsername()).orElseThrow(() -> new ResNotFoundException("User", "email", passwordDto.getUsername()));
		user.setPassword(this.passwordEncoder.encode(passwordDto.getPassword()));
		User updatedUser = this.userRepo.save(user);
		return this.modelMapper.map(updatedUser, UserDto.class);
	}
	
}
