package com.example.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entities.Category;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.CategoryDto;
import com.example.blog.repositories.CategoryRepo;
import com.example.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
		Category existingCategory = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));
		
		existingCategory.setCategoryTitle(categoryDto.getCategoryTitle());
		existingCategory.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoryRepo.save(existingCategory);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer id) {
		Category existingCategory = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));
		this.categoryRepo.delete(existingCategory);
	}

	@Override
	public CategoryDto getCategory(Integer id) {
		Category existingCategory = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));
		return this.modelMapper.map(existingCategory, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

}
