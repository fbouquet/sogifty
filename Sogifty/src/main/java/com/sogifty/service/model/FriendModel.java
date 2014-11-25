package com.sogifty.service.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sogifty.dao.dto.Friend;
import com.sogifty.util.serialization.JsonDateDeserializer;
import com.sogifty.util.serialization.JsonDateSerializer;

public class FriendModel {
	private Integer id;
	private String name;
	private Date birthdate;
	
	public FriendModel() {};
	
	public FriendModel(Friend friend) {
		super();
		this.id = friend.getId();
		this.name = friend.getName();
		this.birthdate = friend.getBirthdate();
	}

	public FriendModel(Integer id) {
		this.setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getBirthdate() {
		return birthdate;
	}

	@JsonDeserialize(using=JsonDateDeserializer.class)
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}
