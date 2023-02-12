package com.example.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	private Integer categoryId;
	
	@NotBlank
	@Size(min=4,message="Category Title must have atleast 4 characters")
	private String categoryTitle;
	
	@NotBlank
	@Size(min=8,message="Category Description must have atleast 8 characters")
	private String categoryDescription;
}
