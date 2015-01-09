package com.sogifty.model;

import java.util.Set;

public class Config {
	
	Set<String> tags;
	int nbTags;
	int nbGifts;
	
	public Set<String> getTags() {
		return tags;
	}
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
	public int getNbTags() {
		return nbTags;
	}
	public void setNbTags(int nbTags) {
		this.nbTags = nbTags;
	}
	public int getNbGifts() {
		return nbGifts;
	}
	public void setNbGifts(int nbGifts) {
		this.nbGifts = nbGifts;
	}

}
