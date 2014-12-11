package com.sogifty.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "preferences")
public class Preferences  implements DTO  {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nb_tags")
	private Integer numberOfTags;
	
	@Column(name = "nb_gifts")
	private Integer numberOfGifts;

	public Integer getId() {
		return id;
	}
	
	public Preferences setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getNumberOfTags() {
		return numberOfTags;
	}

	public Preferences setNumberOfTags(Integer numberOfTags) {
		this.numberOfTags = numberOfTags;
		return this;
	}

	public Integer getNumberOfGifts() {
		return numberOfGifts;
	}

	public Preferences setNumberOfGifts(Integer numberOfGifts) {
		this.numberOfGifts = numberOfGifts;
		return this;
	}
	
	public Preferences updateFields(Object objectToUpdate, Object updatedObject) {
		Preferences preferencesToUpdate = (Preferences) objectToUpdate;
		Preferences updatedPreferences = (Preferences) updatedObject;
		return preferencesToUpdate.setNumberOfTags(updatedPreferences.getNumberOfTags())
								  .setNumberOfGifts(updatedPreferences.getNumberOfGifts());
	}
}
