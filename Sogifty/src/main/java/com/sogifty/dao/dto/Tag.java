package com.sogifty.dao.dto;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(	name = "match", 
				joinColumns = {@JoinColumn(name = "tag_id")},
				inverseJoinColumns = {@JoinColumn(name = "gift_id")})
	private Set<Gift> gifts;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public Set<Gift> getGifts() {
		return gifts;
	}

	public void setGifts(Set<Gift> gifts) {
		this.gifts = gifts;
	}

	@Override
	public String toString() {
		return id + " - " + label;
	}
}
