package com.sogifty.dao.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Friend implements DTO<Friend> {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "birthdate")
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "app_user_id")
	@JsonBackReference
	private User appUser;
	
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

	public Friend updateFields(Friend updatedObject, Friend obj) {
		updatedObject.setId(obj.getId())
					 .setName(obj.getName())
					 .setBirthdate(obj.getBirthdate());
		return updatedObject;
	}
	
	@Override
	public String toString() {
		return "(" + id + ") - " + name + " - " + birthdate + " - "+ "(appUser: "+ appUser.toString() +")";
	}
}
