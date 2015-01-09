package com.sogifty.service.model;

import com.sogifty.dao.dto.Tag;


public class TagModel {
	private String label;

	public TagModel() {};

	public TagModel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

	public TagModel setLabel(String label) {
		this.label = label;
		return this;
	}

	public Tag toTag() {
		return new Tag().setLabel(label);
	}
}
