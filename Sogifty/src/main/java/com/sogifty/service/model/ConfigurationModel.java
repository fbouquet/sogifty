package com.sogifty.service.model;

import java.util.Set;

public class ConfigurationModel {
	private Set<TagModel> tags;
	private PreferencesModel preferences;
	
	public ConfigurationModel() {};
	
	public ConfigurationModel(Set<TagModel> tags, PreferencesModel preferences) {
		this.tags = tags;
		this.preferences = preferences;
	}

	public Set<TagModel> getTags() {
		return tags;
	}
	
	public ConfigurationModel setTags(Set<TagModel> tags) {
		this.tags = tags;
		return this;
	}
	
	public PreferencesModel getPreferences() {
		return preferences;
	}
	
	public ConfigurationModel setPreferences(PreferencesModel preferences) {
		this.preferences = preferences;
		return this;
	}
}
