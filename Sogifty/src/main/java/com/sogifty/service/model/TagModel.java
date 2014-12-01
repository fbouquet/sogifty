package com.sogifty.service.model;

import com.sogifty.dao.dto.Tag;


public class TagModel {
	//private Integer id;
	private String label;

	public TagModel() {};

	public TagModel(Tag tag) {
		//this.id = tag.getId();
		this.label = tag.getLabel();
	}
	
//	public TagModel(Integer id) {
//		this.setId(id);
//	}
	
//	public Integer getId() {
//		return id;
//	}

//	public TagModel setId(Integer id) {
//		this.id = id;
//		return this;
//	}

	public String getLabel() {
		return label;
	}

	public TagModel setLabel(String label) {
		this.label = label;
		return this;
	}

	public Tag toTag() {
		return new Tag().setLabel(label);//.setId(this.id)
	}
}
