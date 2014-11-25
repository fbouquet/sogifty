package com.sogifty.dao.dto;

public interface DTO<T>{
	// Ensures AbstractDAO that getId is always accessible in a DTO object
	public Integer getId();
	public T updateFields(T updatedObject, T obj);
}
