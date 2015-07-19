package com.sogifty.dao;

import com.sogifty.dao.dto.Preferences;

/**
 * Data Access Object dealing with the Preferences objects
 **/
public class PreferencesDAO extends AbstractDAO<Preferences> {

	@Override
	public Class<Preferences> getType() {
		return Preferences.class;
	}
}
