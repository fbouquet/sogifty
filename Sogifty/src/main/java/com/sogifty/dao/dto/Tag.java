package com.sogifty.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinTable(
	   name = "enjoy", 
	   joinColumns = @JoinColumn(name = "tag_id"), 
	   inverseJoinColumns = @JoinColumn(name = "friend_id")
	 )
	private Friend friend;
	
	public Tag() {};

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
	
	public Friend getFriend() {
		return friend;
	}

	public Tag setFriend(Friend friend) {
		this.friend = friend;
		return this;
	}

	public Tag updateFields(Object objectToUpdate, Object updatedObject) {
		Tag friendToUpdate = (Tag) objectToUpdate;
		Tag updatedFriend = (Tag) updatedObject;
		return friendToUpdate.setLabel(updatedFriend.getLabel());
	}
	
	@Override
	public String toString() {
		return "(" + id + ") - " + label + "(friend: "+ friend.toString() +")";
	}
}
