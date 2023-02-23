package com.example.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fieldName;
	private String fieldValue;
	
	public ResNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s with %s = %s not found", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	

}
