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
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "avatar_path")
	private String avatarPath;
	
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

	public String getFirstName() {
		return firstName;
	}

	public Friend setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public Friend setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
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

	public Friend updateFields(Object objectToUpdate, Object updatedObject) {
		Friend friendToUpdate = (Friend) objectToUpdate;
		Friend updatedFriend = (Friend) updatedObject;
		return friendToUpdate.setName(updatedFriend.getName())
							 .setFirstName(updatedFriend.getFirstName())
							 .setAvatarPath(updatedFriend.getAvatarPath())
							 .setBirthdate(updatedFriend.getBirthdate())
							 .setTags(updatedFriend.getTags());
	}
	
	public Set<Tag> getTags() {
		return tags;
	}

	public Friend setTags(Set<Tag> tags) {
		this.tags = tags;
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("(%d) - %s - %s - %s - (appUser: %s)", id, name, firstName, birthdate, appUser);
	}
}
