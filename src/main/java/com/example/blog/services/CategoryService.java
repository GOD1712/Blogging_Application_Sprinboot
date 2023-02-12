package com.example.blog.services;

import java.util.List;

import com.example.blog.payloads.CategoryDto;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto, Integer id);
	void deleteCategory(Integer id);
	CategoryDto getCategory(Integer id);
	List<CategoryDto> getAllCategories();
}
