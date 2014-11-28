package com.sogifty.dao.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sogifty.util.serialization.JsonDateDeserializer;
import com.sogifty.util.serialization.JsonDateSerializer;

@Entity
@Table(name = "friend")
public class Friend implements DTO {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "birthdate")
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "app_user_id")
	@JsonBackReference
	private User appUser;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "enjoy", 
				joinColumns = {@JoinColumn(name = "friend_id")},
				inverseJoinColumns = {@JoinColumn(name = "tag_id")})
	private Set<Tag> tags = new HashSet<Tag>(0);
	
	public Friend() {};

	public Integer getId() {
		return id;
	}

	public Friend setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Friend setName(String name) {
		this.name = name;
		return this;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getBirthdate() {
		return birthdate;
	}

	@JsonDeserialize(using=JsonDateDeserializer.class)
	public Friend setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
		return this;
	}
	
	public User getUser() {
		return appUser;
	}

	public Friend setUser(User appUser) {
		this.appUser = appUser;
		return this;
	}
	
	public Set<Tag> getTags() {
		return tags;
	}
	
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Friend updateFields(Object objectToUpdate, Object updatedObject) {
		Friend friendToUpdate = (Friend) objectToUpdate;
		Friend updatedFriend = (Friend) updatedObject;
		return friendToUpdate.setName(updatedFriend.getName())
							 .setBirthdate(updatedFriend.getBirthdate());
	}
	
	@Override
	public String toString() {
		return "(" + id + ") - " + name + " - " + birthdate + " - "+ "(appUser: "+ appUser.toString() +")";
	}
}
