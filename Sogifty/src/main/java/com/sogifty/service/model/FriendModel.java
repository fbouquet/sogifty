package com.sogifty.service.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sogifty.dao.dto.Friend;
import com.sogifty.dao.dto.Tag;
import com.sogifty.util.serialization.JsonDateDeserializer;
import com.sogifty.util.serialization.JsonDateSerializer;

public class FriendModel {
	private Integer id;
	private String name;
	private String firstName;
	private String avatarPath;
	private Date birthdate;
	private Set<TagModel> tags;
	
	public FriendModel() {};
	
	public FriendModel(Friend friend) {
		this.id = friend.getId();
		this.name = friend.getName();
		this.firstName = friend.getFirstName();
		this.avatarPath = friend.getAvatarPath();
		this.birthdate = friend.getBirthdate();
		
		Set<TagModel> tags = new HashSet<TagModel>(0);
		for (Tag tag : friend.getTags()) {
			tags.add(new TagModel(tag));
		}
		this.setTags(tags);
	}

	public FriendModel(Integer id) {
		this.setId(id);
	}

	public Integer getId() {
		return id;
	}

	public FriendModel setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public FriendModel setName(String name) {
		this.name = name;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public FriendModel setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public FriendModel setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
		return this;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getBirthdate() {
		return birthdate;
	}

	@JsonDeserialize(using=JsonDateDeserializer.class)
	public FriendModel setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
		return this;
	}

	public Set<TagModel> getTags() {
		return tags;
	}

	public FriendModel setTags(Set<TagModel> tags) {
		this.tags = tags;
		return this;
	}
}
