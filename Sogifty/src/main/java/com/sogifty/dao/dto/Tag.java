package com.sogifty.dao.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
public class Tag implements DTO {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "label")
	private String label;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
	private Set<Gift> gifts;

	public Integer getId() {
		return id;
	}

	public Tag setId(int id) {
		this.id = id;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public Tag setLabel(String label) {
		this.label = label;
		return this;
	}

	public Set<Gift> getGifts() {
		return gifts;
	}

	public Tag setGifts(Set<Gift> gifts) {
		this.gifts = gifts;
		return this;
	}

	public void addGift(Gift gift) {
		if(gifts == null)
			gifts = new HashSet<Gift>();
		gifts.add(gift);
	}

	public Tag updateFields(Object objectToUpdate, Object updatedObject) {
		Tag tagToUpdate = (Tag) objectToUpdate;
		Tag updatedTag = (Tag) updatedObject;
		return tagToUpdate.setLabel(updatedTag.getLabel())
				.setGifts(updatedTag.getGifts());
	}

	@Override
	public String toString() {
		return "(" + id + ") - " + label;
	}
}
