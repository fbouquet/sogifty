package com.sogifty.service.model;

public class UserModel {
	private Integer id;
	
	public UserModel(Integer id) {
		this.setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
