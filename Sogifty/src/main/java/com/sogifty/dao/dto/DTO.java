package com.sogifty.dao.dto;

/**
 * Represents a DTO suitable to our DAO abstraction model.
 */
public interface DTO {
	/**
	 * Get the if of the entity. Ensures AbstractDAO that getId is always accessible in a DTO object
	 * @return The id of the entity.
	 */
	public Integer getId();
	
	/**
	 * Update the fields of an entity.
	 * @param objectToUpdate The entity to be updated.
	 * @param updatedObject The updated entity.
	 * @return The entity updated.
	 */
	public Object updateFields(Object objectToUpdate, Object updatedObject);
}
