package com.example.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String field;
	private Integer fieldValue;
	
	public ResourceNotFoundException(String resourceName, String field, Integer fieldValue) {
		super(String.format("%s not found with %s: %s", resourceName, field, fieldValue));
		this.resourceName = resourceName;
		this.field = field;
		this.fieldValue = fieldValue;
	}
	
	
}
