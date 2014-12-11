package com.sogifty.service.model;

import com.sogifty.dao.dto.Preferences;

public class PreferencesModel {

	private Integer numberOfTags;
	private Integer numberOfGifts;
	
	public PreferencesModel() {};

	public PreferencesModel(Preferences preferences) {
		this.numberOfTags = preferences.getNumberOfTags();
		this.numberOfGifts = preferences.getNumberOfGifts();
	}
	
	public Integer getNumberOfTags() {
		return numberOfTags;
	}

	public PreferencesModel setNumberOfTags(Integer numberOfTags) {
		this.numberOfTags = numberOfTags;
		return this;
	}

	public Integer getNumberOfGifts() {
		return numberOfGifts;
	}

	public PreferencesModel setNumberOfGifts(Integer numberOfGifts) {
		this.numberOfGifts = numberOfGifts;
		return this;
	};
}
