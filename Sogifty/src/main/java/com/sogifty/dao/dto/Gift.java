package com.sogifty.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gift")
public class Gift implements DTO {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int		id;
	
	@Column(name = "name")
	private String	name;
	
	@Column(name = "description")
	private String	description;
	
	@Column(name = "price")
	private String	price;
	
	@Column(name = "picture_url")
	private String	pictureUrl;
	
	@Column(name = "url")
	private String	url;

	public String getName() {
		return name;
	}

	public Gift setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Gift setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getPrice() {
		return price;
	}

	public Gift setPrice(String price) {
		this.price = price;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Gift setUrl(String url) {
		this.url = url;
		return this;
	}

	public Gift setId(int id) {
		this.id = id;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public Gift setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
		return this;
	}
	
	@Override
	public String toString() {
		return "Gift [id=" + id + ", name=" + name + ", description="
				+ description + ", price=" + price + ", pictureUrl="
				+ pictureUrl + ", url=" + url + "]";
	}

	public Object updateFields(Object objectToUpdate, Object updatedObject) {
		Gift giftToUpdate = (Gift) objectToUpdate;
		Gift updatedGift = (Gift) updatedObject;
		return giftToUpdate
				.setName(updatedGift.getName())
				.setDescription(updatedGift.getDescription())
				.setPictureUrl(updatedGift.getPictureUrl())
				.setPrice(updatedGift.getPrice())
				.setUrl(updatedGift.getUrl());
	}
}
