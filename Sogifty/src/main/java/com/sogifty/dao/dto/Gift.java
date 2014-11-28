package com.sogifty.dao.dto;

import java.util.Date;

public class Gift {
	public int id;
	public String name;
	public String description;
	public String price;
	public String pictureUrl;
	public String url;
	public Date creation;
	public Date lastUpdate;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getPrice() {
		return price;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public String getUrl() {
		return url;
	}
	public Date getCreation() {
		return creation;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	@Override
	public String toString() {
		return "Gift [id=" + id + ", name=" + name + ", description="
				+ description + ", price=" + price + ", pictureUrl="
				+ pictureUrl + ", url=" + url + ", creation=" + creation
				+ ", lastUpdate=" + lastUpdate + "]";
	}
	
	
}
