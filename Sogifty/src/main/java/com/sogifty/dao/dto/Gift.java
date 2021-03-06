package com.sogifty.dao.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@Column(name = "creation")
	private Date creation;
	
	@Column(name = "last_update")
	private Date lastUpdate;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(	name = "match", 
				joinColumns = {@JoinColumn(name = "gift_id", nullable = false, updatable = false)},
				inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false, updatable = false)})
	private List<Tag> tags;
	
	@Column(name = "website")
	@Enumerated(EnumType.STRING)
	private Website website;

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
	
	public Date getCreation() {
		return creation;
	}

	public Gift setCreation(Date creation) {
		this.creation = creation;
		return this;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public Gift setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
		return this;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public Gift setTags(List<Tag> tags) {
		this.tags = tags;
		return this;
	}
	
	public void addTag(Tag tag) {
		tag.addGift(this);
		if(tags == null)
			tags = new ArrayList<Tag>();
		tags.add(tag);
	}

	@Override
	public String toString() {
		return "Gift [id=" + id + ", name=" + name + ", description="
				+ description + ", price=" + price + ", pictureUrl="
				+ pictureUrl + ", url=" + url + ", creation=" + creation
				+ ", lastUpdate=" + lastUpdate + "]";
	}

	public Object updateFields(Object objectToUpdate, Object updatedObject) {
		Gift giftToUpdate = (Gift) objectToUpdate;
		Gift updatedGift = (Gift) updatedObject;
		return giftToUpdate
				.setName(updatedGift.getName())
				.setDescription(updatedGift.getDescription())
				.setPictureUrl(updatedGift.getPictureUrl())
				.setPrice(updatedGift.getPrice())
				.setUrl(updatedGift.getUrl())
				.setLastUpdate(updatedGift.getLastUpdate())
				.setCreation(updatedGift.getCreation())
				.setTags(updatedGift.getTags());
	}
}
