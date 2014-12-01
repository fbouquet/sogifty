package com.sogifty.dao.dto;

public interface DTO {
	// Ensures AbstractDAO that getId is always accessible in a DTO object
	public Integer getId();
	public Object updateFields(Object objectToUpdate, Object updatedObject);
}
