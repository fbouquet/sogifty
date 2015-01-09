package com.sogifty.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.sogifty.dao.PreferencesDAO;
import com.sogifty.dao.TagDAO;
import com.sogifty.dao.dto.Preferences;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.model.ConfigurationModel;
import com.sogifty.service.model.PreferencesModel;
import com.sogifty.service.model.TagModel;

public class ConfigurationService {

	private static final int DEFAULT_PREFERENCES_ID = 1;
	private TagDAO tagDAO = new TagDAO();
	private PreferencesDAO preferencesDAO = new PreferencesDAO();
	
	public ConfigurationModel getConfiguration() throws SogiftyException {
		Set<TagModel> tags = getServerTags();
		Preferences preferences = preferencesDAO.getById(new Integer(DEFAULT_PREFERENCES_ID));
		
		return new ConfigurationModel(tags, new PreferencesModel(preferences));
	}

	private Set<TagModel> getServerTags() throws SogiftyException {
		Set<TagModel> tags = new HashSet<TagModel>(0);
		Iterator<String> tagsIterator = tagDAO.getAllLabels().iterator();

		while (tagsIterator.hasNext()) {
			tags.add(new TagModel(tagsIterator.next()));
		}
		
		return tags;
	}
}
