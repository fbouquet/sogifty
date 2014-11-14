package com.sogifty.webservice.model;

public class UserServiceModel {
	private Integer id;
	
	public UserServiceModel(Integer id) {
		this.setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
