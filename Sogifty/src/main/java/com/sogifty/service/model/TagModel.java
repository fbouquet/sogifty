package com.sogifty.service.model;

import com.sogifty.dao.dto.Tag;


public class TagModel {
	private Integer id;
	private String label;
	
	public TagModel() {};
	
	public TagModel(Tag tag) {
		super();
		this.id = tag.getId();
		this.label = tag.getLabel();
	}

	public TagModel(Integer id) {
		this.setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
