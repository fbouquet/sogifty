package com.sogifty.dao.dto;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name = "app_user")
public class User implements DTO {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "pwd")
	private String pwd;
	
	@Column(name = "email")
	private String email;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "appUser", cascade = CascadeType.ALL)
	@Column(nullable = true)
	@JsonManagedReference
	private Set<Friend> friends;
	
	public User() {};

	public Integer getId() {
		return id;
	}

	public User setId(int id) {
		this.id = id;
		return this;
	}

	public String getPwd() {
		return pwd;
	}

	public User setPwd(String pwd) {
		this.pwd = pwd;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	@JsonIgnore
	public Set<Friend> getFriends() {
		return friends;
	}
	
	@Override
	public String toString() {
		return "(" + id + ") - " + pwd + " - " + email;
	}

	public User updateFields(Object objectToUpdate, Object updatedObject) {
		User userToUpdate = (User) objectToUpdate;
		User updatedUser = (User) updatedObject;
		return userToUpdate.setPwd(updatedUser.getPwd())
				   		   .setEmail(updatedUser.getEmail());
	}
}
