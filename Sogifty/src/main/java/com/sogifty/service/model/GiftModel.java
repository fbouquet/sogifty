package com.sogifty.service.model;

import com.sogifty.dao.dto.Gift;


public class GiftModel {
	private int		id;
	private String	name;
	private String	description;
	private String	price;
	private String	pictureUrl;
	private String	url;

	public GiftModel(Gift gift) {
		this.id				= gift.getId();
		this.name			= gift.getName();
		this.description	= gift.getDescription();
		this.price			= gift.getPrice();
		this.pictureUrl		= gift.getPictureUrl();
		this.url			= gift.getUrl();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
}
